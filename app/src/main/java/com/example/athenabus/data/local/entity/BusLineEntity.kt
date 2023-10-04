package com.example.athenabus.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class BusLineEntity(
    @PrimaryKey val LineCode: String,
    val LineDescr: String,
    val LineDescrEng: String,
    val LineID: String,
    val isFavorite: Boolean
)