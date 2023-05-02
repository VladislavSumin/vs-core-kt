package ru.vs.core.uikit.local_configuration

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember

@Composable
actual fun LocalConfigurationHolder(content: @Composable () -> Unit) {
    val composeWindow = LocalComposeWindow.current
    val windowSize = composeWindow.size

    val configuration = remember { ConfigurationImpl() }
    configuration.screenWidth = windowSize.width
    configuration.screenHeight = windowSize.height

    CompositionLocalProvider(LocalConfiguration provides configuration, content = content)
}
