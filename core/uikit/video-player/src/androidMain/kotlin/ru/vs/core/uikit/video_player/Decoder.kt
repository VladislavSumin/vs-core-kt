package ru.vs.core.uikit.video_player

import android.media.MediaCodec
import android.media.MediaCodecInfo
import android.media.MediaCodecList
import android.media.MediaFormat
import android.view.Surface
import io.github.oshai.KotlinLogging
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext
import java.nio.ByteBuffer


internal class Decoder(
    private val source: Playable.FlowOfByteArrays,
    private val scope: CoroutineScope,
) {

    private fun selectCodec(mimeType: String) {
        val numCodecs = MediaCodecList.getCodecCount()
        for (i in 0 until numCodecs) {
            val codecInfo = MediaCodecList.getCodecInfoAt(i)
            if (!codecInfo.supportedTypes.contains("video/avc")) continue
            if (codecInfo.isEncoder) continue
            println("====================")
            println(codecInfo.name)
            println(codecInfo.canonicalName)
            println("====================")
        }
    }

    fun init(surface: Surface) {
        selectCodec("video/avc")
        logger.info { "init()" }
        val codec = MediaCodec.createByCodecName("c2.android.avc.decoder")
        val format = MediaFormat()
        format.setString(MediaFormat.KEY_MIME, "video/avc")
        format.setInteger(MediaFormat.KEY_WIDTH, 800)
        format.setInteger(MediaFormat.KEY_HEIGHT, 600)
        codec.configure(format, surface, null, 0)
        codec.start()

        scope.launch(newSingleThreadContext("writter")) {
            var bufferIndex = -1
            var buffer: ByteBuffer? = null
            var time = 0L

            fun takeBuffer(zerosCount: Int) {
                while (bufferIndex < 0) {
                    bufferIndex = codec.dequeueInputBuffer(30000)
                }
                buffer = codec.getInputBuffer(bufferIndex)!!
                check(buffer!!.position() == 0)
                repeat(zerosCount) {
                    buffer!!.put(0)
                }
                buffer!!.put(1)
            }

            fun releaseBuffer() {
                logger.info { "put() pos = ${buffer!!.position()}" }
                codec.queueInputBuffer(bufferIndex, 0, buffer!!.position(), time, 0)
                time += 1000000
                buffer = null
                bufferIndex = -1
            }

            fun updateBuffer(zerosCount: Int) {
                releaseBuffer()
                takeBuffer(zerosCount)
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
                            logger.info { "zeros count = $zerosCount" }
                            if (zerosCount > 2) zerosCount = 2
                            buffer!!.position(buffer!!.position() - zerosCount - 2)
                            updateBuffer(zerosCount)
                            logger.info { "naluType = $b, v2= ${(b.toInt() and 0b1111111).toByte()}" }

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

            takeBuffer(zerosCount)
            source.data.collect { inputByteArray ->
                processNextInput(inputByteArray)
            }
        }
        scope.launch(newSingleThreadContext("reader")) {
            val newBufferInfo = MediaCodec.BufferInfo()
            while (true) {
                var index = -1
                while (index < 0) {
                    index = codec.dequeueOutputBuffer(newBufferInfo, 30000)
                }
                logger.info { "get()" }
                codec.releaseOutputBuffer(index, true)
                delay(8)
            }
        }

    }

    companion object {
        private val logger = KotlinLogging.logger("MediaDecoder")
    }
}