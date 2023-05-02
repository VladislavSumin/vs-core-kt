package ru.vs.core.uikit.local_configuration

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.referentialEqualityPolicy
import androidx.compose.ui.window.FrameWindowScope
import androidx.compose.ui.window.WindowState

internal val LocalComposeWindow = compositionLocalOf<WindowState>(referentialEqualityPolicy()) {
    error("No local provider for LocalComposeWindow")
}

@Composable
@Suppress("UnusedReceiverParameter")
fun FrameWindowScope.LocalComposeWindowHolder(windowState: WindowState, content: @Composable () -> Unit) {
    CompositionLocalProvider(LocalComposeWindow provides windowState, content = content)
}
