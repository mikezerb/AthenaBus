package com.example.athenabus.domain.model

data class Stop(
    val StopCode: String,
    val StopDescr: String,
    val StopDescrEng: String,
    val StopStreet: String,
    val StopLat: String,
    val StopLng: String,
    val StopID: String,
    val distance: String
)