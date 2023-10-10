package com.example.athenabus.domain.model

data class Route(
    val RouteCode: String,
    val LineCode: String,
    val RouteDescr: String,
    val RouteDescrEng: String,
    val RouteDistance: String,
    val RouteType: String,
    val LineID: String,
    val hidden: String,
    val LineDescr: String,
    val LineDescrEng: String,
    val MasterLineCode: String,
)