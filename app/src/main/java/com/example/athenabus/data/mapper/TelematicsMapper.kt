package com.example.athenabus.data.mapper

import com.example.athenabus.data.remote.dto.LineDtoItem
import com.example.athenabus.domain.model.Line

fun LineDtoItem.toLine(): Line {
    return Line(
        LineID = LineID,
        LineDescr = LineDescr,
        LineDescrEng = LineDescrEng
    )
}
