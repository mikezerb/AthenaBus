package com.example.athenabus.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class RouteEntity(
    @PrimaryKey val RouteCode: String,
    val LineCode: String,
    val RouteDescr: String,
    val RouteDescrEng: String,
    val RouteDistance: String,
    val RouteType: String,
)