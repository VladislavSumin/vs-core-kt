package ru.vs.core.navigation

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavigationSupportTopAppBar(
    title: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    actions: @Composable RowScope.() -> Unit = {},
    windowInsets: WindowInsets = TopAppBarDefaults.windowInsets,
    colors: TopAppBarColors = TopAppBarDefaults.topAppBarColors(),
    scrollBehavior: TopAppBarScrollBehavior? = null
) {
    TopAppBar(
        title = title,
        modifier = modifier,
        navigationIcon = {
            val navigationContext = LocalNavigationContext.current
            val navigationType by navigationContext.navigationType.collectAsState()
            when (val navigation = navigationType) {
                is NavigationContext.NavigationType.Drawer -> {
                    IconButton(onClick = { navigation.toggleDrawer() }) {
                        Icon(Icons.Default.Menu, "Toggle drawer")
                    }
                }

                is NavigationContext.NavigationType.Simple -> {
                    IconButton(onClick = { navigation.back() }) {
                        Icon(Icons.Default.ArrowBack, "Back")
                    }
                }

                NavigationContext.NavigationType.None -> Unit
            }
        },
        actions = actions,
        windowInsets = windowInsets,
        colors = colors,
        scrollBehavior = scrollBehavior
    )
}