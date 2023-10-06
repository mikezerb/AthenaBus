package com.example.athenabus.domain.repository

import com.example.athenabus.common.Resource
import com.example.athenabus.domain.model.Line
import com.example.athenabus.domain.model.Stop
import kotlinx.coroutines.flow.Flow

interface BusLineRepository {
    fun getBusLines(): Flow<Resource<List<Line>>>
    fun getLineFromDB(searchTxt: String): Flow<Resource<List<Line>>>
    fun getLineCodesFromLineID(lineID: String): Flow<Resource<List<String>>>
    suspend fun toggleFavoriteLine(lineID: String)

    fun getStopsFromXY(latitude: String, longitude: String): Flow<Resource<List<Stop>>>
}