package com.example.athenabus.data.local

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bedtime
import androidx.compose.material.icons.filled.DirectionsBus
import androidx.compose.material.icons.filled.FastForward
import androidx.compose.material.icons.filled.FlightTakeoff
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material.icons.filled.Tram
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.athenabus.R

enum class LineCategory(
    @StringRes val titleResId: Int,
    val iconResId: ImageVector,
    val color: Color = Color.Transparent,
) {
    UNKNOWN(R.string.unknown_category_title, Icons.Default.Stop, Color.Transparent),
    BUS(R.string.bus_category_title, Icons.Default.DirectionsBus, Color(0xFF0C84BE)),
    TROLLEY(R.string.trolley_category_title, Icons.Default.Tram, Color(0xFF9C27B0)),
    HOUR_24(R.string.hour24_category_title, Icons.Default.Schedule, Color(0xFF2E7D32)),
    EXPRESS(R.string.express_category_title, Icons.Default.FastForward, Color(0xFFE64A19)),
    NIGHT(R.string.night_category_title, Icons.Default.Bedtime, Color(0xFF1A237E)),
    AIRPORT(R.string.aeroplane_category_title, Icons.Default.FlightTakeoff, Color(0xFF01579B)),
}