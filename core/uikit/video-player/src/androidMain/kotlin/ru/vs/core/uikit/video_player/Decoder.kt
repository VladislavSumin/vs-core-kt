package ru.vs.core.uikit.video_player

import android.media.MediaCodec
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
        logger.info { "init()" }
        val codec = MediaCodec.createDecoderByType("video/avc")
        val format = MediaFormat()
        format.setString(MediaFormat.KEY_MIME, "video/avc")
        format.setInteger(MediaFormat.KEY_WIDTH, 1920)
        format.setInteger(MediaFormat.KEY_HEIGHT, 1080)
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
                logger.info { "put()" }
                codec.queueInputBuffer(
                    index,
                    0,
                    buffer!!.position(),
                    time, 0b01
                )
                time += 100000000000
                buffer = null
                index = -1
            }

            fun updateBuffer() {
                releaseBuffer()
                takeBuffer()
            }

            takeBuffer()
            source.data.collect { inputByteArray ->
                if (buffer!!.remaining() < inputByteArray.size || buffer!!.position() > 200_000) updateBuffer()
                buffer!!.put(inputByteArray)
            }
        }
        scope.launch(newSingleThreadContext("reader")) {
                val newBufferInfo = MediaCodec.BufferInfo()
            while (true) {
                var index = -1
                while (index < 0) {
                    logger.info { "11()" }

                    index = codec.dequeueOutputBuffer(newBufferInfo, 3000000)
                }
                logger.info { "get()" }
                codec.releaseOutputBuffer(index, true)
                logger.info { "get() done" }
            }
        }

    }

    companion object {
        private val logger = KotlinLogging.logger("MediaDecoder")
    }
}