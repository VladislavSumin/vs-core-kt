package ru.vs.core.uikit.local_configuration

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.dp

@Composable
actual fun LocalConfigurationHolder(content: @Composable () -> Unit) {

    val configuration = remember { ConfigurationImpl() }

    // TODO now just hardcode for test js run, need add correct listener
    configuration.screenWidth = 1200.dp
    configuration.screenHeight = 600.dp

    CompositionLocalProvider(LocalConfiguration provides configuration, content = content)
}
