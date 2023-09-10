package com.example.athenabus.data.remote.dto

import com.example.athenabus.domain.model.Line

data class LineDtoItem(
    val LineCode: String,
    val LineDescr: String,
    val LineDescrEng: String,
    val LineID: String
)

fun LineDtoItem.toLine(): Line{
    return Line(
        LineID = LineID,
        LineDescr = LineDescr,
        LineDescrEng = LineDescrEng
    )
}