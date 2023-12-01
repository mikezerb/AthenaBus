package com.example.athenabus.data.local

import androidx.annotation.StringRes
import com.example.athenabus.R

enum class LineCategory(@StringRes val titleResId: Int) {
    UNKNOWN(R.string.unknown_category_title),
    BUS(R.string.bus_category_title),
    TROLLEY(R.string.trolley_category_title),
    HOUR_24(R.string.hour24_category_title),
    NIGHT(R.string.night_category_title),
    EXPRESS(R.string.express_category_title),
    AIRPORT(R.string.aeroplane_category_title),
}