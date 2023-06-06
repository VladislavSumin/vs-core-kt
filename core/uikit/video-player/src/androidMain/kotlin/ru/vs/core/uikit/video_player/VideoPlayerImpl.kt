package ru.vs.core.uikit.video_player

import android.view.SurfaceHolder
import android.view.SurfaceView
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import io.github.oshai.KotlinLogging

private val logger = KotlinLogging.logger("VideoPlayer")

@Composable
internal actual fun VideoPlayerImpl(
    source: Playable,
    modifier: Modifier,
) {
    val scope = rememberCoroutineScope()

    val decoder = remember(source, scope) {
        Decoder(source as Playable.FlowOfByteArrays, scope)
    }

    val surfaceCallback = remember {
        object : SurfaceHolder.Callback {
            override fun surfaceCreated(holder: SurfaceHolder) {
                logger.info { "surfaceCreated()" }
                decoder.init(holder.surface)
            }

            override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
                logger.info { "surfaceChanged(format=$format, width=$width, height=$height)" }
            }

            override fun surfaceDestroyed(holder: SurfaceHolder) {
                logger.info { "surfaceDestroyed()" }
            }
        }
    }

    AndroidView(
        factory = { context ->
            logger.info { "AndroidView.factory()" }
            SurfaceView(context).also { surfaceView ->
                surfaceView.holder.addCallback(surfaceCallback)
            }
        },
        modifier
    ) {

    }
}