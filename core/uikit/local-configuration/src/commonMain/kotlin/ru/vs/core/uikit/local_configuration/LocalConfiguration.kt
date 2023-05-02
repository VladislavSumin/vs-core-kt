package ru.vs.core.uikit.local_configuration

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.referentialEqualityPolicy
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

val LocalConfiguration = compositionLocalOf<Configuration>(referentialEqualityPolicy()) {
    error("Local configuration not set")
}

interface Configuration {
    val screenWidth: Dp
    val screenHeight: Dp
}

internal class ConfigurationImpl : Configuration {
    override var screenWidth: Dp by mutableStateOf(0.dp)
    override var screenHeight: Dp by mutableStateOf(0.dp)
}
