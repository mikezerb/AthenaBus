package com.example.athenabus.presentation.bus_list.components

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.example.athenabus.data.local.LineCategory
import com.google.android.material.color.MaterialColors

@Composable
fun LineCategoryChip(
    modifier: Modifier = Modifier,
    category: LineCategory,
    context: Context = LocalContext.current
) {
    val shape = RoundedCornerShape(corner = CornerSize(16.dp))

    val backgroundColor = when (category) {
        LineCategory.BUS -> Color(
            MaterialColors.harmonizeWithPrimary(
                context,
                Color(0xFF0C84BE).toArgb()
            )
        ) // Harmonized red for bus
        LineCategory.TROLLEY -> Color(
            MaterialColors.harmonizeWithPrimary(
                context,
                Color(0xFF9C27B0).toArgb()
            )
        ) // Harmonized red for bus
        LineCategory.HOUR_24 -> Color(
            MaterialColors.harmonizeWithPrimary(
                context,
                Color(0xFF2E7D32).toArgb()
            )
        )

        LineCategory.EXPRESS -> Color(
            MaterialColors.harmonizeWithPrimary(
                context,
                Color(0xFFE64A19).toArgb()
            )
        )

        LineCategory.NIGHT -> Color(
            MaterialColors.harmonizeWithPrimary(
                context,
                Color(0xFF1A237E).toArgb()
            )
        )

        LineCategory.AIRPORT -> Color(
            MaterialColors.harmonizeWithPrimary(
                context,
                Color(0xFF01579B).toArgb()
            )
        )

        LineCategory.UNKNOWN -> Color.Gray
    }
    if (category != LineCategory.UNKNOWN) {
        Row(
            modifier
                .clip(shape)
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.outlineVariant,
                    shape = shape
                )
                .background(color = backgroundColor)
                .padding(horizontal = 12.dp)
        ) {
            Text(
                text = stringResource(id = category.titleResId),
                color = Color.White,
                style = MaterialTheme.typography.titleSmall,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
        }
    }
}

@PreviewLightDark
@Composable
private fun PreviewLineCategoryChip() {
    Surface {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Row {
                LineCategoryChip(category = LineCategory.BUS)
                LineCategoryChip(category = LineCategory.TROLLEY)
                LineCategoryChip(category = LineCategory.NIGHT)
            }
            Row {
                LineCategoryChip(category = LineCategory.HOUR_24)
                LineCategoryChip(category = LineCategory.EXPRESS)
                LineCategoryChip(category = LineCategory.AIRPORT)
            }
        }
    }
}