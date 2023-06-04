package ru.vs.core.uikit.video_player

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun VideoPlayer(
    source: Playable,
    modifier: Modifier = Modifier,
) = VideoPlayerImpl(
    source = source,
    modifier = modifier,
)

@Composable
internal expect fun VideoPlayerImpl(
    source: Playable,
    modifier: Modifier,
)


interface Playable {

}