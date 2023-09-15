package com.example.athenabus.data.remote

import com.example.athenabus.data.remote.dto.LineDto
import com.example.athenabus.data.remote.dto.RouteDto
import retrofit2.http.GET
import retrofit2.http.Query

interface OASATelematicsAPI {

    @GET("api/?act=webGetLines")
    suspend fun getBusLines(): LineDto

    @GET("api/")
    suspend fun getRoutesForLine(
        @Query("act") action: String = "getRoutesForLine",
        @Query("p1") lineCode: String
    ): RouteDto

}