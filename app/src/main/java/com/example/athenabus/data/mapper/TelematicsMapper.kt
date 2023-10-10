package com.example.athenabus.data.mapper

import com.example.athenabus.data.local.entity.BusLineEntity
import com.example.athenabus.data.local.entity.RouteEntity
import com.example.athenabus.data.remote.dto.ArrivalStopDtoItem
import com.example.athenabus.data.remote.dto.ClosestStopDtoItem
import com.example.athenabus.data.remote.dto.LineDtoItem
import com.example.athenabus.data.remote.dto.RouteDtoItem
import com.example.athenabus.domain.model.Arrival
import com.example.athenabus.domain.model.Line
import com.example.athenabus.domain.model.Route
import com.example.athenabus.domain.model.Stop

fun LineDtoItem.toBusLineEntity(): BusLineEntity {
    return BusLineEntity(
        LineID = LineID,
        LineDescr = LineDescr,
        LineDescrEng = LineDescrEng,
        LineCode = LineCode,
        isFavorite = false
    )
}

fun BusLineEntity.toBusLine(): Line {
    return Line(
        LineCode = LineCode,
        LineID = LineID,
        LineDescr = LineDescr,
        LineDescrEng = LineDescrEng,
        isFavorite = isFavorite
    )
}

fun RouteDtoItem.toRoute(): Route {
    return Route(
        RouteCode = RouteCode,
        LineCode = LineCode,
        RouteDescr = RouteDescr,
        RouteDescrEng = RouteDescrEng,
        RouteDistance = RouteDistance,
        RouteType = RouteType,
        LineID = LineID,
        hidden = hidden,
        LineDescr = LineDescr,
        LineDescrEng = LineDescrEng,
        MasterLineCode = MasterLineCode
    )
}

fun RouteDtoItem.toRouteEntity(): RouteEntity {
    return RouteEntity(
        RouteCode = RouteCode,
        LineCode = LineCode,
        RouteDescr = RouteDescr,
        RouteDescrEng = RouteDescrEng,
        RouteDistance = RouteDistance,
        RouteType = RouteType
    )
}

//fun RouteEntity.toRoute(): Route {
//    return Route(
//        RouteCode = RouteCode,
//        LineCode = LineCode,
//        RouteDescr = RouteDescr,
//        RouteDescrEng = RouteDescrEng,
//        RouteDistance = RouteDistance,
//        RouteType = RouteType,
//        LineID = LineID
//    )
//}

fun Route.toRouteEntity(): RouteEntity {
    return RouteEntity(
        RouteCode = RouteCode,
        LineCode = LineCode,
        RouteDescr = RouteDescr,
        RouteDescrEng = RouteDescrEng,
        RouteDistance = RouteDistance,
        RouteType = RouteType
    )
}

fun ClosestStopDtoItem.toStop(): Stop {
    return Stop(
        StopCode = StopCode,
        StopDescr = StopDescr,
        StopDescrEng = StopDescrEng,
        StopID = StopID,
        StopStreet = StopStreet,
        StopLat = StopLat,
        StopLng = StopLng,
        distance = distance
    )
}

fun ArrivalStopDtoItem.toArrival(): Arrival {
    return Arrival(
        route_code = route_code,
        btime2 = btime2,
        veh_code = veh_code,
        RouteCode = "",
        LineCode = "",
        RouteDescr = "",
        RouteDescrEng = "",
        RouteDistance = "",
        RouteType = "",
        LineID = "",
        hidden = "",
        LineDescr = "",
        LineDescrEng = "",
        MasterLineCode = ""
    )
}
