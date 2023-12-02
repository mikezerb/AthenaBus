package com.example.athenabus.data.mapper

import com.example.athenabus.data.local.entity.BusLineEntity
import com.example.athenabus.data.local.entity.FavoriteLinesEntity
import com.example.athenabus.data.local.entity.RouteEntity
import com.example.athenabus.data.remote.dto.ArrivalStopDtoItem
import com.example.athenabus.data.remote.dto.ClosestStopDtoItem
import com.example.athenabus.data.remote.dto.Come
import com.example.athenabus.data.remote.dto.DailyScheduleDto
import com.example.athenabus.data.remote.dto.Go
import com.example.athenabus.data.remote.dto.LineDtoItem
import com.example.athenabus.data.remote.dto.RouteDtoItem
import com.example.athenabus.data.remote.dto.StopDetailsDtoItem
import com.example.athenabus.data.remote.dto.WebGetRouteDtoItem
import com.example.athenabus.data.remote.dto.WebGetStopsDtoItem
import com.example.athenabus.domain.model.Arrival
import com.example.athenabus.domain.model.ComeUI
import com.example.athenabus.domain.model.DailySchedule
import com.example.athenabus.domain.model.GoUI
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
        isFavorite = isFavorite,
        Category = Category
    )
}

fun FavoriteLinesEntity.toBusLine(): Line {
    return Line(
        LineCode = LineCode,
        LineID = LineID,
        LineDescr = LineDescr,
        LineDescrEng = LineDescrEng,
        isFavorite = false,
        Category = Category
    )
}

fun RouteDtoItem.toRoute(): Route {
    return Route(
        LineID = LineID,
        RouteCode = RouteCode,
        LineCode = LineCode,
        RouteDescr = RouteDescr,
        RouteDescrEng = RouteDescrEng,
        RouteDistance = RouteDistance,
        RouteType = RouteType,
        hidden = hidden,
        LineDescr = LineDescr,
        LineDescrEng = LineDescrEng,
        MasterLineCode = MasterLineCode
    )
}


fun WebGetRouteDtoItem.toRoute(): Route {
    return Route(
        RouteCode = RouteCode,
        RouteDescr = RouteDescr,
        RouteDescrEng = RouteDescrEng,
        RouteDistance = RouteDistance,
        RouteType = RouteType,
        MasterLineCode = "",
        LineCode = LineCode,
        LineDescr = "",
        LineID = "",
        LineDescrEng = "",
        hidden = ""
    )
}

fun WebGetStopsDtoItem.toStop(): Stop {
    return Stop(
        StopCode = StopCode,
        StopID = StopID,
        StopDescr = StopDescr,
        StopDescrEng = StopDescrEng,
        StopStreet = StopStreet,
        StopLat = StopLat,
        StopLng = StopLng,
        distance = ""
    )
}

fun StopDetailsDtoItem.toStop(stopCode: String): Stop {
    return Stop(
        StopCode = stopCode,
        StopID = stop_id,
        StopDescr = stop_descr,
        StopDescrEng = stop_descr_matrix_eng,
        StopStreet = "",
        StopLat = stop_lat,
        StopLng = stop_lng,
        distance = ""
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

fun Line.toFavoriteLineEntity(): FavoriteLinesEntity {
    return FavoriteLinesEntity(
        LineID = LineID,
        LineCode = LineCode,
        LineDescr = LineDescr,
        LineDescrEng = LineDescrEng
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

fun DailyScheduleDto.toDailySchedule(): DailySchedule {
    return DailySchedule(
        come = come.map { it.toComeUI() },
        go = go.map { it.toGoUI() }
    )
}

fun Go.toGoUI(): GoUI {
    return GoUI(
        time = sdd_start1
    )
}

fun Come.toComeUI(): ComeUI {
    return ComeUI(
        time = sdd_start1 ?: "122"
    )
}
