package ru.vs.core.uikit.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.WindowInfo
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun WindowInfo.screenWidth(): WindowSize {
//    val density = LocalDensity.current
//    val width = with(density) { containerSize.width.toDp() }
//    return when {
//        width < 600.dp -> WindowSize.Small
//        width < 1240.dp -> WindowSize.Medium
//        else -> WindowSize.Big
//    }
    TODO()
}

enum class WindowSize {
    Small,
    Medium,
    Big,
}
