package com.example.athenabus.data.mapper

import com.example.athenabus.data.local.entity.BusLineEntity
import com.example.athenabus.data.remote.dto.LineDtoItem
import com.example.athenabus.data.remote.dto.RouteDtoItem
import com.example.athenabus.domain.model.Line
import com.example.athenabus.domain.model.Route

fun LineDtoItem.toBusLineEntity(): BusLineEntity {
    return BusLineEntity(
        LineID = LineID,
        LineDescr = LineDescr,
        LineDescrEng = LineDescrEng,
        LineCode = LineCode
    )
}

fun BusLineEntity.toBusLine(): Line {
    return Line(
        LineCode = LineCode,
        LineID = LineID,
        LineDescr = LineDescr,
        LineDescrEng = LineDescrEng
    )
}

fun RouteDtoItem.toRoute(): Route {
    return Route(
        route_code = route_code,
        route_descr = route_descr,
        route_descr_eng = route_descr_eng
    )
}
