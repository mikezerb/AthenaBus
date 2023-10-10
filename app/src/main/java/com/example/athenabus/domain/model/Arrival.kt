package com.example.athenabus.domain.model

data class Arrival(
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
    val btime2: String,
    val route_code: String,
    val veh_code: String,
    val hidden: String
)