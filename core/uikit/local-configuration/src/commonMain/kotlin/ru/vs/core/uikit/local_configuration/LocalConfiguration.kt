package ru.vs.core.uikit.local_configuration

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.derivedStateOf
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

    val screenWidthSize: ScreenSize

    enum class ScreenSize {
        Small,
        Medium,
        Big,
    }
}

internal class ConfigurationImpl : Configuration {
    override var screenWidth: Dp by mutableStateOf(0.dp)
    override var screenHeight: Dp by mutableStateOf(0.dp)

    override val screenWidthSize: Configuration.ScreenSize by derivedStateOf {
        val width = screenWidth
        when {
            width < 600.dp -> Configuration.ScreenSize.Small
            width < 1240.dp -> Configuration.ScreenSize.Medium
            else -> Configuration.ScreenSize.Big
        }
    }
}
