package ru.vs.core.uikit.video_player

import android.view.SurfaceView
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import io.github.oshai.kotlinlogging.KotlinLogging

private val logger = KotlinLogging.logger("VideoPlayer")

@Composable
internal actual fun VideoPlayerImpl(
    source: Playable,
    modifier: Modifier,
) {
    val decoder = remember(source) {
        Decoder(source as Playable.FlowOfByteArrays)
    }

    AndroidView(
        factory = { context ->
            logger.info { "AndroidView.factory()" }
            SurfaceView(context).also { surfaceView ->
                surfaceView.holder.addCallback(decoder)
            }
        },
        modifier
    ) {
        // no action
    }
}