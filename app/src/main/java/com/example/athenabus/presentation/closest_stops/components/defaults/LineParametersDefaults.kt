package com.example.athenabus.presentation.closest_stops.components.defaults

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

object LineParametersDefaults {

    private val defaultLinearGradient = 3.dp

    fun linearGradient(
        strokeWidth: Dp = defaultLinearGradient,
        startColor: Color,
        endColor: Color,
        startY: Float = 0.0f,
        endY: Float = Float.POSITIVE_INFINITY
    ): LineParameters {
        val brush = Brush.verticalGradient(
            colors = listOf(startColor, endColor),
            startY = startY,
            endY = endY
        )
        return LineParameters(strokeWidth, brush)
    }
}

data class LineParameters(
    val strokeWidth: Dp,
    val brush: Brush
)