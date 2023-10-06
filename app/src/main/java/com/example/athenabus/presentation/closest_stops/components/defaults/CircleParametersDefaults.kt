package com.example.athenabus.presentation.closest_stops.components.defaults

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

private val defaultCircleRadius = 12.dp

fun circleParameters(
    radius: Dp = defaultCircleRadius,
    backgroundColor: Color = Color.Cyan,
    stroke: StrokeParameters? = null,
    @DrawableRes
    icon: Int? = null
) = CircleParameters(
    radius,
    backgroundColor,
    stroke,
    icon
)

data class StrokeParameters(
    val color: Color,
    val width: Dp
)

data class CircleParameters(
    val radius: Dp,
    val backgroundColor: Color,
    val stroke: StrokeParameters? = null,
    @DrawableRes val icon: Int? = null
)