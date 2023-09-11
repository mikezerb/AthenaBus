package com.example.athenabus.data.mapper

import com.example.athenabus.data.local.entity.BusLineEntity
import com.example.athenabus.data.remote.dto.LineDtoItem
import com.example.athenabus.domain.model.Line

fun LineDtoItem.toLine(): Line {
    return Line(
        LineID = LineID,
        LineDescr = LineDescr,
        LineDescrEng = LineDescrEng
    )
}

fun LineDtoItem.toBusLineEntity(): BusLineEntity {
    return BusLineEntity(
        LineID = LineID,
        LineDescr = LineDescr,
        LineDescrEng = LineDescrEng,
        LineCode = LineCode
    )
}

fun BusLineEntity.toBusLine(): Line{
    return Line(
        LineID = LineID,
        LineDescr = LineDescr,
        LineDescrEng = LineDescrEng
    )
}
