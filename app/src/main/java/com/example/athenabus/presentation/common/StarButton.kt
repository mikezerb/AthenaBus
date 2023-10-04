package com.example.athenabus.presentation.common

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun FavoriteButton(
    modifier: Modifier,
    onClick: () -> Unit,
    isFavorite: Boolean = false
) {
    var buttonState: StarButtonState by remember { mutableStateOf(if (isFavorite) StarButtonState.PRESSED else StarButtonState.IDLE) }

    val shape = RoundedCornerShape(corner = CornerSize(16.dp))

    val transition = updateTransition(
        targetState = buttonState,
        label = "JoinButtonTransition"
    )
    val duration = 700
    val buttonBackgroundColor: Color
            by transition.animateColor(
                transitionSpec = { tween(duration) },
                label = "Button Background Color"
            ) { state ->
                when (state) {
                    StarButtonState.IDLE -> MaterialTheme.colorScheme.surface
                    StarButtonState.PRESSED -> MaterialTheme.colorScheme.primary
                }
            }
    val buttonWidth: Dp
            by transition.animateDp(
                transitionSpec = { tween(duration) },
                label = "Button Width"
            ) { state ->
                when (state) {
                    StarButtonState.IDLE -> 38.dp // increase if button has text
                    StarButtonState.PRESSED -> 28.dp
                }
            }
    val textMaxWidth: Dp
            by transition.animateDp(
                transitionSpec = { tween(duration) },
                label = "Text Max Width"
            ) { state ->
                when (state) {
                    StarButtonState.IDLE -> 70.dp
                    StarButtonState.PRESSED -> 0.dp
                }
            }

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
    // Button icon
    val iconAsset: ImageVector =
        if (buttonState == StarButtonState.PRESSED)
            Icons.Default.Done
        else
            Icons.Default.StarBorder
    Box(
        modifier = modifier
            .clip(shape)
            .border(width = 1.dp, color = MaterialTheme.colorScheme.outlineVariant, shape = shape)
            .background(color = buttonBackgroundColor)
            .size(width = buttonWidth, height = 24.dp)
            .clickable(onClick = {
                buttonState = if (buttonState == StarButtonState.IDLE) {
                    onClick.invoke()
                    StarButtonState.PRESSED
                } else {
                    onClick.invoke()
                    StarButtonState.IDLE
                }
            }),
        contentAlignment = Alignment.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = iconAsset,
                contentDescription = "Plus Icon",
                tint = iconTintColor,
                modifier = Modifier.size(16.dp)
            )
//            Text(
//                text = stringResource(R.string.favorite_btn_add),
//                color = MaterialTheme.colorScheme.onSurface,
//                fontSize = 14.sp,
//                maxLines = 1,
//                modifier = Modifier.widthIn(
//                    min = 0.dp,
//                    max = textMaxWidth // here
//                )
//            )
        }
    }
}


enum class StarButtonState {
    IDLE,
    PRESSED
}
