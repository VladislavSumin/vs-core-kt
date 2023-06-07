package ru.vs.core.uikit.video_player

import android.media.MediaCodec
import android.media.MediaExtractor
import android.media.MediaFormat
import android.view.Surface
import io.github.oshai.KotlinLogging
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext
import java.nio.ByteBuffer


internal class Decoder(
    private val source: Playable.FlowOfByteArrays,
    private val scope: CoroutineScope,
) {

    fun init(surface: Surface) {
        val codec = MediaCodec.createDecoderByType("video/avc")
        val format = MediaFormat()
        format.setString(MediaFormat.KEY_MIME, "video/avc")
        format.setInteger(MediaFormat.KEY_WIDTH, 2920)
        format.setInteger(MediaFormat.KEY_HEIGHT, 2080)
        codec.configure(format, surface, null, 0)
        codec.start()

        scope.launch(newSingleThreadContext("writter")) {
            var index = -1
            var buffer: ByteBuffer? = null
            var time = 0L

            fun takeBuffer() {
                while (index < 0) {
                    index = codec.dequeueInputBuffer(30000)
                }
                buffer = codec.getInputBuffer(index)!!
            }

            fun releaseBuffer() {
                codec.queueInputBuffer(index, 0, buffer!!.position(), time, 0)
                time += 1
                buffer = null
                index = -1
            }

            fun updateBuffer() {
                releaseBuffer()
                takeBuffer()
            }


            takeBuffer()
            source.data.collect { inputByteArray ->

                if (buffer!!.remaining() < inputByteArray.size || buffer!!.position()>200_000) updateBuffer()
                buffer!!.put(inputByteArray)

//                var index = -1
//                while (index < 0) {
//                    index = codec.dequeueInputBuffer(30000)
//                }
//                logger.info { "put()" }
//                val buffer = codec.getInputBuffer(index)!!
//                check(buffer.limit() >= inputByteArray.size)
//                check(buffer.position() == 0)
//                buffer.put(inputByteArray)
//                codec.queueInputBuffer(index, 0, inputByteArray.size, 0, 0)
            }
        }
        scope.launch(newSingleThreadContext("reader")) {
            while (true) {
                val newBufferInfo = MediaCodec.BufferInfo()
                var index = -1
                while (index < 0) {
                    index = codec.dequeueOutputBuffer(newBufferInfo, 30000)
                }
                logger.info { "get()" }
                codec.releaseOutputBuffer(index, true)
            }
        }

    }

    fun close() {

    }

    companion object {
        private val logger = KotlinLogging.logger("MediaDecoder")
    }
}