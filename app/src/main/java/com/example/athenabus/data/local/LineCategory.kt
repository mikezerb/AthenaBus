package com.example.athenabus.data.local

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bedtime
import androidx.compose.material.icons.filled.DirectionsBus
import androidx.compose.material.icons.filled.FastForward
import androidx.compose.material.icons.filled.FlightTakeoff
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material.icons.filled.Tram
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.athenabus.R

enum class LineCategory(@StringRes val titleResId: Int, val iconResId: ImageVector) {
    UNKNOWN(R.string.unknown_category_title, Icons.Default.Stop),
    BUS(R.string.bus_category_title, Icons.Default.DirectionsBus),
    TROLLEY(R.string.trolley_category_title, Icons.Default.Tram),
    HOUR_24(R.string.hour24_category_title, Icons.Default.Schedule),
    NIGHT(R.string.night_category_title, Icons.Default.Bedtime),
    EXPRESS(R.string.express_category_title, Icons.Default.FastForward),
    AIRPORT(R.string.aeroplane_category_title, Icons.Default.FlightTakeoff),
}