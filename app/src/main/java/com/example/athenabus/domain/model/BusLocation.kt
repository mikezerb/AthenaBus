package com.example.athenabus.domain.model

import androidx.compose.runtime.Stable

@Stable
data class BusLocation(
//    val CS_DATE: String, not needed for now
    val CS_LAT: String,
    val CS_LNG: String,
    val ROUTE_CODE: String,
    val VEH_NO: String
)
