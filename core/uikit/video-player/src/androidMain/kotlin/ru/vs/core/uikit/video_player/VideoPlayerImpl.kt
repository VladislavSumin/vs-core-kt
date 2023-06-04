package ru.vs.core.uikit.video_player

import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier

internal actual fun VideoPlayerImpl(
    url: String,
    isResumed: Boolean,
    volume: Float,
    speed: Float,
    seek: Float,
    isFullscreen: Boolean,
    progressState: MutableState<Progress>,
    modifier: Modifier,
    onFinish: (() -> Unit)?
) {
    TODO()
}