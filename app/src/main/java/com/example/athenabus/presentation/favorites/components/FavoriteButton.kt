package com.example.athenabus.presentation.favorites.components

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.athenabus.presentation.common.StarButtonState

@Composable
fun FavoriteButton(
    isFavorite: Boolean = false,
    onClick: (Boolean) -> Unit,
) {

    var buttonState: StarButtonState by remember { mutableStateOf(if (isFavorite) StarButtonState.PRESSED else StarButtonState.IDLE) }
    val transition = updateTransition(
        targetState = buttonState,
        label = "JoinButtonTransition"
    )
    val duration = 700

    val iconTintColor: Color
            by transition.animateColor(
                transitionSpec = { tween(duration) },
                label = "Icon Tint Color"
            ) { state ->
                when (state) {
                    StarButtonState.IDLE -> MaterialTheme.colorScheme.secondary
                    StarButtonState.PRESSED -> MaterialTheme.colorScheme.onPrimary
                }
            }

    IconToggleButton(
        checked = isFavorite,
        onCheckedChange = {
            buttonState = if (buttonState == StarButtonState.IDLE) {
                onClick.invoke(isFavorite)
                StarButtonState.PRESSED
            } else {
                onClick.invoke(isFavorite)
                StarButtonState.IDLE
            }
        })
     {
        Icon(
            tint = iconTintColor,
            imageVector = if (isFavorite)
                Icons.Filled.Star
            else
                Icons.Default.StarBorder,
            contentDescription = null
        )
    }
}

enum class StarButtonState {
    IDLE,
    PRESSED
}


@Preview(name = "FavoriteButton")
@Composable
private fun PreviewFavoriteButton() {
}