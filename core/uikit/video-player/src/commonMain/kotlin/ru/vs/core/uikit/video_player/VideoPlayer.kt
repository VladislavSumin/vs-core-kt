package ru.vs.core.uikit.video_player

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import kotlinx.coroutines.flow.Flow

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

/**
 * Any source types who can be played
 */
sealed interface Playable {
    data class FlowOfByteArrays(val data: Flow<ByteArray>) : Playable
}