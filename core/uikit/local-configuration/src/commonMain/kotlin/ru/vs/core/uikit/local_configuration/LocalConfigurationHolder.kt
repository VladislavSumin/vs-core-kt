package ru.vs.core.uikit.local_configuration

import androidx.compose.runtime.Composable

/**
 * Holds and updates [Configuration]
 */
@Composable
expect fun LocalConfigurationHolder(content: @Composable () -> Unit)