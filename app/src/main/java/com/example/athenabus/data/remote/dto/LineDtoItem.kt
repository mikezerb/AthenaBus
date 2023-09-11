package com.example.athenabus.data.remote.dto

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("bus_line")
data class LineDtoItem(
    @PrimaryKey val LineCode: String,
    val LineDescr: String,
    val LineDescrEng: String,
    val LineID: String
)
