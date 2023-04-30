package ru.vs.core.uikit.dropdown_menu

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.MenuItemColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.material3.DropdownMenuItem as DropdownMenuItemPlatform

@Composable
actual fun DropdownMenuItem(
    text: @Composable () -> Unit,
    onClick: () -> Unit,
    modifier: Modifier,
    leadingIcon: @Composable (() -> Unit)?,
    trailingIcon: @Composable (() -> Unit)?,
    enabled: Boolean,
    colors: MenuItemColors,
    contentPadding: PaddingValues,
    interactionSource: MutableInteractionSource,
) {
    DropdownMenuItemPlatform(
        text, onClick, modifier, leadingIcon, trailingIcon, enabled, colors, contentPadding, interactionSource
    )
}