package com.example.athenabus.domain.repository

import com.example.athenabus.common.Resource
import com.example.athenabus.domain.model.Arrival
import com.example.athenabus.domain.model.BusLocation
import com.example.athenabus.domain.model.DailySchedule
import com.example.athenabus.domain.model.Line
import com.example.athenabus.domain.model.Route
import com.example.athenabus.domain.model.Stop
import kotlinx.coroutines.flow.Flow

interface BusLineRepository {
    fun getBusLines(): Flow<Resource<List<Line>>>
    fun getLineFromDB(searchTxt: String): Flow<Resource<Line>>
    fun getLineCodesFromLineID(lineID: String): Flow<Resource<List<String>>>
    fun getLinesFromLineID(lineID: String): Flow<Resource<List<Line>>>
    suspend fun toggleFavoriteLine(lineID: String)
    fun getStopsFromXY(latitude: String, longitude: String): Flow<Resource<List<Stop>>>
    fun getStopArrivals(stopCode: String): Flow<Resource<List<Arrival>>>
    fun getRoutesForStop(stopCode: String): Flow<Resource<List<Route>>>
    fun getStopsFromRoute(routeCode: String): Flow<Resource<List<Stop>>>
    fun getDailySchedules(lineCode: String): Flow<Resource<DailySchedule>>
    fun getFavoriteLines(): Flow<Resource<List<Line>>>
    suspend fun addFavoriteLine(line: Line)
    suspend fun isFavoriteLine(line: String): Boolean
    suspend fun removeFavoriteLine(line: String)
    fun getFavoriteStops(): Flow<Resource<List<Stop>>>
    suspend fun addFavoriteStop(stop: Stop)
    suspend fun isFavoriteStop(stopCode: String): Boolean
    suspend fun removeFavoriteStop(stopCode: String)
    fun getStopDetailsFromCode(routeCode: String): Flow<Resource<Stop>>
    fun getBusLocation(routeCode: String): Flow<Resource<List<BusLocation>>>
}
