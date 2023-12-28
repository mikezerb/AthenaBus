package com.example.athenabus.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FavoriteStopsEntity(
    @PrimaryKey val StopCode: String,
    val StopID: String,
    val StopDescr: String,
    val StopDescrEng: String,
    val StopLat: String,
    val StopLng: String
)
