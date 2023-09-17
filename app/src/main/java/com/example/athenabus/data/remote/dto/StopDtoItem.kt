package com.example.athenabus.data.remote.dto

data class StopDtoItem(
    val RouteStopOrder: String,
    val StopAmea: String,
    val StopCode: String,
    val StopDescr: String,
    val StopDescrEng: String,
    val StopHeading: String,
    val StopID: String,
    val StopLat: String,
    val StopLng: String,
    val StopStreet: String,
    val StopStreetEng: Any,
    val StopType: String
)