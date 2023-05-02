package ru.vs.core.uikit.local_configuration

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.LocalConfiguration as LocalConfigurationPlatform

@Composable
actual fun LocalConfigurationHolder(content: @Composable () -> Unit) {
    val androidConfiguration = LocalConfigurationPlatform.current

    val configuration = remember { ConfigurationImpl() }
    configuration.screenWidth = androidConfiguration.screenWidthDp.dp
    configuration.screenHeight = androidConfiguration.screenHeightDp.dp

    CompositionLocalProvider(LocalConfiguration provides configuration, content = content)
}