package ru.vs.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Stable
import androidx.compose.runtime.staticCompositionLocalOf
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

val LocalNavigationContext = staticCompositionLocalOf<NavigationContext> {
    error("Local navigation not set")
}

@Stable
interface NavigationContext {
    val navigationType: StateFlow<NavigationType>

    sealed interface NavigationType {
        /**
         * Navigation drawer navigation used.
         *
         * Use this to open navigation drawer callback.
         */
        class Drawer(private val onToggleDrawer: () -> Unit) : NavigationType {
            /**
             * Toggle drawer state
             */
            fun toggleDrawer() = onToggleDrawer()
        }

        /**
         * Simple navigation (use arrow back)
         */
        class Simple(private val onBack: () -> Unit) : NavigationType {
            /**
             * Navigate back by [NavigationContext] provider
             */
            fun back() = onBack()
        }

        /**
         * No navigation
         */
        data object None : NavigationType
    }
}

private class StaticNavigation(navigationType: NavigationContext.NavigationType) : NavigationContext {
    override val navigationType: StateFlow<NavigationContext.NavigationType> = MutableStateFlow(navigationType)
}

@Composable
fun WithSimpleNavigation(
    onBack: () -> Unit,
    block: @Composable () -> Unit,
) {
    CompositionLocalProvider(
        LocalNavigationContext provides StaticNavigation(NavigationContext.NavigationType.Simple(onBack))
    ) {
        block()
    }
}

@Composable
fun WithDrawerNavigation(
    onToggleDrawer: () -> Unit,
    block: @Composable () -> Unit,
) {
    CompositionLocalProvider(
        LocalNavigationContext provides StaticNavigation(NavigationContext.NavigationType.Drawer(onToggleDrawer))
    ) {
        block()
    }
}
