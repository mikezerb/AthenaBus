package com.example.athenabus.data.remote

import com.example.athenabus.data.remote.dto.ArrivalStopDto
import com.example.athenabus.data.remote.dto.BusLocationDto
import com.example.athenabus.data.remote.dto.ClosestStopDto
import com.example.athenabus.data.remote.dto.DailyScheduleDto
import com.example.athenabus.data.remote.dto.LineDto
import com.example.athenabus.data.remote.dto.RouteDto
import com.example.athenabus.data.remote.dto.StopDetailsDto
import com.example.athenabus.data.remote.dto.WebGetRouteDto
import com.example.athenabus.data.remote.dto.WebGetStopsDto
import retrofit2.http.GET
import retrofit2.http.Query

interface OASATelematicsAPI {

    @GET("api/?act=webGetLines")
    suspend fun getBusLines(): LineDto

    @GET("api/")
    suspend fun getRoutesForLine(
        @Query("act") action: String = "webGetRoutes",
        @Query("p1") lineCode: String
    ): WebGetRouteDto

    @GET("api/")
    suspend fun getStopsForRoute(
        @Query("act") action: String = "webGetStops",
        @Query("p1") routeCode: String
    ): WebGetStopsDto

    @GET("api/")
    suspend fun getClosestStops(
        @Query("act") action: String = "getClosestStops",
        @Query("p1") x: String,
        @Query("p2") y: String
    ): ClosestStopDto

    @GET("api/")
    suspend fun getStopArrivals(
        @Query("act") action: String = "getStopArrivals",
        @Query("p1") stopCode: String,
    ): ArrivalStopDto?

    @GET("api/")
    suspend fun webRoutesForStop(
        @Query("act") action: String = "webRoutesForStop",
        @Query("p1") stopCode: String,
    ): RouteDto

    @GET("api/")
    suspend fun getDailySchedule(
        @Query("act") action: String = "getDailySchedule",
        @Query("line_code") lineCode: String,
    ): DailyScheduleDto

    @GET("api/")
    suspend fun getStopNameAndXY(
        @Query("act") action: String = "getStopNameAndXY",
        @Query("p1") stopCode: String,
    ): StopDetailsDto


    @GET("api/")
    suspend fun getBusLocation(
        @Query("act") action: String = "getBusLocation",
        @Query("p1") routeCode: String,
    ): BusLocationDto


}
