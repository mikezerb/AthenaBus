package com.example.athenabus.presentation.bus_list.components

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.athenabus.data.local.LineCategory
import com.google.android.material.color.MaterialColors

@Composable
fun BusLineIDType(
    modifier: Modifier = Modifier,
    lineId: String = "",
    category: LineCategory = LineCategory.UNKNOWN,
    context: Context = LocalContext.current
) {
    val shape = RoundedCornerShape(corner = CornerSize(12.dp))

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
    Row(
        modifier
            .wrapContentWidth()
            .clip(shape)
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.outlineVariant,
                shape = shape
            )
            .background(color = backgroundColor)
            .padding(horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            imageVector = category.iconResId,
            contentDescription = null,
            tint = Color.White
        )
        Text(
            modifier = Modifier
                .drawBehind {
                    drawRoundRect(
                        color = backgroundColor,
                        cornerRadius = CornerRadius(16.dp.toPx())
                    )
                }
                .defaultMinSize(minWidth = 52.dp),
            text = lineId,
            textAlign = TextAlign.Center,
            color = Color.White,
            style = MaterialTheme.typography.headlineMedium
        )
    }

}

@Preview(name = "BusLineIDType")
@Composable
private fun PreviewBusLineIDType() {
    Column {
        BusLineIDType(lineId = "608", category = LineCategory.BUS)
        BusLineIDType(lineId = "123", category = LineCategory.EXPRESS)
        BusLineIDType(lineId = "X95", category = LineCategory.AIRPORT)
        BusLineIDType(lineId = "421", category = LineCategory.TROLLEY)
        BusLineIDType(lineId = "708", category = LineCategory.NIGHT)
        BusLineIDType(lineId = "89", category = LineCategory.HOUR_24)
    }
}