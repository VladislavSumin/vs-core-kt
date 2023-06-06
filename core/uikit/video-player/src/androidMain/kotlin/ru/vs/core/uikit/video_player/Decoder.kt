package ru.vs.core.uikit.video_player

import android.media.MediaCodec
import android.media.MediaExtractor
import android.media.MediaFormat
import android.view.Surface
import io.github.oshai.KotlinLogging
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext


internal class Decoder(
    private val source: Playable.FlowOfByteArrays,
    private val scope: CoroutineScope,
) {

    fun init(surface: Surface) {
        val codec = MediaCodec.createByCodecName("video/avc")
        val format = MediaFormat()
        format.setString(MediaFormat.KEY_MIME, "video/avc")
        format.setInteger(MediaFormat.KEY_WIDTH, 800)
        format.setInteger(MediaFormat.KEY_HEIGHT, 600)
        codec.configure(format, surface, null, 0)

//        scope.launch(newSingleThreadContext("writter")) {
//            source.data.collect { inputByteArray ->
//                var index = -1
//                while (index < 0) {
//                    index = codec.dequeueInputBuffer(30000)
//                }
//                val buffer = codec.getInputBuffer(index)!!
//                buffer.
//            }
//        }
//        scope.launch(newSingleThreadContext("reader")) { }

    }

    fun close() {

    }

    companion object {
        private val logger = KotlinLogging.logger("MediaDecoder")
    }
}