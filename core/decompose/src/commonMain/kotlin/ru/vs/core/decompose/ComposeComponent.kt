package ru.vs.core.decompose

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.ComponentContext

/**
 * Component with support self rendering
 *
 * For project using compose only we don't need to separate view layer.
 * To keep composable content module internal use this interface
 * In conjunction with the MVIKotlin, this allows keep Store and Models internal without any additional mapping
 */
interface ComposeComponent : ComponentContext {
    // We can't use default value for modifier here, because compose now doesn't support default values
    // for abstract composable functions
    @Composable
    fun Render(modifier: Modifier)
}
