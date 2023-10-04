package com.example.athenabus.domain.model

data class Line(
    val LineCode: String,
    val LineID: String,
    val LineDescr: String,
    val LineDescrEng: String,
    val isFavorite: Boolean
)