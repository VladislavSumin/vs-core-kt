package ru.vs.core.uikit.letter_avatar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import ru.vs.core.uikit.auto_size_text.AutoSizeText

@Composable
fun LetterAvatar(
    text: String,
    modifier: Modifier = Modifier,
    shape: Shape = CircleShape,
) {
    Box(
        modifier
            .clip(shape)
            .background(MaterialTheme.colorScheme.primaryContainer)
    ) {
        AutoSizeText(
            text,
            Modifier.align(Alignment.Center)
        )
    }
}