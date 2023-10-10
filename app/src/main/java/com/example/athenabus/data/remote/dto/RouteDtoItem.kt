package com.example.athenabus.data.remote.dto

data class RouteDtoItem(
    val LineCode: String,
    val LineDescr: String,
    val LineDescrEng: String,
    val LineID: String,
    val MasterLineCode: String,
    val RouteCode: String,
    val RouteDescr: String,
    val RouteDescrEng: String,
    val RouteDistance: String,
    val RouteType: String,
    val hidden: String
)