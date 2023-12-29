package com.example.athenabus.domain.model

import androidx.compose.runtime.Stable

@Stable
data class Stop(
    val StopCode: String,
    val StopDescr: String,
    val StopDescrEng: String?,
    val StopStreet: String?,
    val StopLat: String,
    val StopLng: String,
    val StopID: String,
    val distance: String
)