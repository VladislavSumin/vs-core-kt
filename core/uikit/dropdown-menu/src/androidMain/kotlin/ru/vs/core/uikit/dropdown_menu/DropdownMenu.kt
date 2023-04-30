package ru.vs.core.uikit.dropdown_menu

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.DpOffset
import androidx.compose.material3.DropdownMenu as DropdownMenuPlatform

@Composable
actual fun DropdownMenu(
    expanded: Boolean,
    onDismissRequest: () -> Unit,
    focusable: Boolean,
    modifier: Modifier,
    offset: DpOffset,
    content: @Composable ColumnScope.() -> Unit
) {
    DropdownMenuPlatform(
        expanded = expanded,
        onDismissRequest = onDismissRequest,
        modifier = modifier,
        offset = offset,
        content = content
    )
}
