package com.example.athenabus.data.remote

import com.example.athenabus.data.remote.dto.LineDto
import retrofit2.http.GET

interface OASATelematicsAPI {

    @GET("api/?act=webGetLines")
    suspend fun getLines() : LineDto

}