package ru.vs.core.uikit.video_player

import android.media.MediaCodec
import android.media.MediaFormat
import android.view.SurfaceHolder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.nio.ByteBuffer


internal class Decoder(
    private val source: Playable.FlowOfByteArrays,
) : SurfaceHolder.Callback {
    private var scope: CoroutineScope? = null

    override fun surfaceCreated(holder: SurfaceHolder) {
        check(scope == null)
        scope = CoroutineScope(Dispatchers.IO)

        scope!!.launch {

            // Don't use create by media type because default codec can't parse given video stream
            val mediaCodec = MediaCodec.createByCodecName("c2.android.avc.decoder")
            mediaCodec.configure(createMediaFormat(), holder.surface, null, 0)
            mediaCodec.start()

            try {
                launch { parseAndSubmitFramesToCodec(mediaCodec) }
                renderDecodedData(mediaCodec)
            } finally {
                mediaCodec.stop()
            }
        }
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
        // no action
    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {
        scope!!.cancel()
        scope = null
    }

    /**
     * Read [MediaCodec.dequeueOutputBuffer] and render its to given surface
     */
    private suspend fun renderDecodedData(mediaCodec: MediaCodec) {
        val newBufferInfo = MediaCodec.BufferInfo()
        while (true) {
            var index = -1
            while (index < 0) {
                index = mediaCodec.dequeueOutputBuffer(newBufferInfo, 30000)
            }
            mediaCodec.releaseOutputBuffer(index, true)

            // TODO pass correct frame rate to here
            delay(8)
        }
    }

    private suspend fun parseAndSubmitFramesToCodec(mediaCodec: MediaCodec) {
        var bufferIndex = -1
        var buffer: ByteBuffer? = null

        fun takeBuffer() {
            while (bufferIndex < 0) {
                bufferIndex = mediaCodec.dequeueInputBuffer(30000)
            }
            buffer = mediaCodec.getInputBuffer(bufferIndex)!!
            check(buffer!!.position() == 0)
            buffer!!.put(0)
            buffer!!.put(0)
            buffer!!.put(1)
        }

        fun releaseBuffer() {
            mediaCodec.queueInputBuffer(bufferIndex, 0, buffer!!.position(), 0, 0)
            buffer = null
            bufferIndex = -1
        }

        fun updateBuffer() {
            releaseBuffer()
            takeBuffer()
        }

        var isNaluFind = false
        var isFirstNaluFind = false
        var zerosCount = 0
        var emitterIndex = 0

        fun findFirstNalu(bArr: ByteArray) {
            while (true) {
                val length = bArr.size
                val i: Int = emitterIndex
                if (length > i) {
                    val b = bArr[i]
                    if (b.toInt() == 0) {
                        zerosCount++
                    } else if (b.toInt() != 1 || zerosCount < 2) {
                        zerosCount = 0
                    } else {
                        zerosCount = 0
                        emitterIndex = i + 1
                        isFirstNaluFind = true
                        return
                    }
                    emitterIndex = i + 1
                } else {
                    return
                }
            }
        }

        fun processInput(bArr: ByteArray) {
            while (true) {
                val length = bArr.size
                val i: Int = emitterIndex
                if (length > i) {
                    val b = bArr[i]
                    buffer!!.put(b)
                    if (isNaluFind) {
                        if (zerosCount > 3) zerosCount = 3
                        buffer!!.position(buffer!!.position() - zerosCount - 2)
                        updateBuffer()

                        isNaluFind = false
                        zerosCount = 0
                        buffer!!.put(b)
                    } else if (b.toInt() == 0) {
                        zerosCount++
                    } else if (b.toInt() != 1 || zerosCount < 2) {
                        zerosCount = 0
                    } else {
                        isNaluFind = true
                    }
                    emitterIndex++
                } else {
                    return
                }
            }
        }

        fun processNextInput(bArr: ByteArray) {
            emitterIndex = 0
            if (!isFirstNaluFind) {
                findFirstNalu(bArr)
            }
            processInput(bArr)
        }

        takeBuffer()
        source.data.collect { inputByteArray ->
            processNextInput(inputByteArray)
        }
    }

    /**
     * Creates [MediaFormat] with default config
     */
    private fun createMediaFormat(): MediaFormat {
        val format = MediaFormat()
        format.setString(MediaFormat.KEY_MIME, "video/avc")

        // It's ok to pass any resolution here
        // Resolution set to correct automatically when codec receive frame with configuration
        format.setInteger(MediaFormat.KEY_WIDTH, 800)
        format.setInteger(MediaFormat.KEY_HEIGHT, 600)
        return format
    }
}
