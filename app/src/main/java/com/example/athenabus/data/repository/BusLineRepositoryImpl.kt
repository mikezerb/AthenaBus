package com.example.athenabus.data.repository

import com.example.athenabus.common.Resource
import com.example.athenabus.data.local.FavoritesDao
import com.example.athenabus.data.local.LineCategory
import com.example.athenabus.data.local.TelematicsLineDao
import com.example.athenabus.data.mapper.toArrival
import com.example.athenabus.data.mapper.toBusLine
import com.example.athenabus.data.mapper.toBusLineEntity
import com.example.athenabus.data.mapper.toDailySchedule
import com.example.athenabus.data.mapper.toFavoriteLineEntity
import com.example.athenabus.data.mapper.toRoute
import com.example.athenabus.data.mapper.toStop
import com.example.athenabus.data.remote.OASATelematicsAPI
import com.example.athenabus.domain.model.Arrival
import com.example.athenabus.domain.model.DailySchedule
import com.example.athenabus.domain.model.Line
import com.example.athenabus.domain.model.Route
import com.example.athenabus.domain.model.Stop
import com.example.athenabus.domain.repository.BusLineRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class BusLineRepositoryImpl @Inject constructor(
    private val api: OASATelematicsAPI,
    private val lineDao: TelematicsLineDao,
    private val favoritesDao: FavoritesDao,
) : BusLineRepository {

    val trolleyList: List<String> = listOf(
        "10", "11", "12", "15",
        "16", "17", "18", "19", "19Β", "20", "21", "24", "25"
    )
    val h24List: List<String> = listOf("040", "11")
    val nightList: List<String> = listOf("040", "11", "500", "790", "Χ14")
    val aeroplaneList: List<String> = listOf("Χ93", "Χ95", "Χ96", "Χ97")
    val expressList: List<String> = listOf("Ε14", "Ε90", "Χ14")

    override fun getBusLines(): Flow<Resource<List<Line>>> = flow {
        emit(Resource.Loading())

        var busLines = lineDao.getBusLines().map { it.toBusLine() }

        if (busLines.isEmpty()) {
            try {
                val remoteBusLines = api.getBusLines()
                lineDao.clearBusLines()
                lineDao.insertBusLines(remoteBusLines.map { it.toBusLineEntity() })

                busLines = lineDao.getBusLines().map { it.toBusLine() }


            } catch (e: HttpException) {
                emit(
                    Resource.Error(
                        message = e.localizedMessage ?: "error",
                        data = emptyList() // Since the database is empty, there is no data to return
                    )
                )
                return@flow
            } catch (e: IOException) {
                emit(
                    Resource.Error(
                        message = "Internet connection error",
                        data = emptyList() // Since the database is empty, there is no data to return
                    )
                )
                return@flow
            }
        }

        // Filter the bus lines to get distinct
        val distinctBusLines = busLines.distinctBy { it.LineID }

        // Assign categories based on lists
        distinctBusLines.forEach { line ->
            when (line.LineID) {
                in trolleyList -> line.Category = LineCategory.TROLLEY
                in h24List -> line.Category = LineCategory.HOUR_24
                in nightList -> line.Category = LineCategory.NIGHT
                in aeroplaneList -> line.Category = LineCategory.AIRPORT
                in expressList -> line.Category = LineCategory.EXPRESS
                else -> line.Category = LineCategory.BUS
            }
        }

        emit(Resource.Success(distinctBusLines))
    }

    override fun getLineFromDB(searchTxt: String): Flow<Resource<Line>> = flow {
        emit(Resource.Loading())

        val busLines = lineDao.searchBusLines(searchTxt).toBusLine()
        emit(Resource.Loading(data = busLines))

        val isDbEmpty = busLines.LineID.isEmpty() && searchTxt.isBlank()

        if (!isDbEmpty) {
            emit(Resource.Success(busLines))
        }

        try {
            val remoteBusLines = api.getBusLines()
            lineDao.clearBusLines()
            lineDao.insertBusLines(remoteBusLines.map { it.toBusLineEntity() })
        } catch (e: HttpException) {
            emit(
                Resource.Error(
                    message = e.localizedMessage ?: "error",
                    data = busLines
                )
            )
        } catch (e: IOException) {
            emit(
                Resource.Error(
                    message = "Internet connection error",
                    data = busLines
                )
            )
        }

        emit(Resource.Success(busLines))
    }

    override fun getLineCodesFromLineID(lineID: String): Flow<Resource<List<String>>> = flow {
        emit(Resource.Loading())

        val busLinesFromDB = lineDao.getLineCodesFromLineID(lineID)

        if (busLinesFromDB.isNotEmpty()) {
            emit(Resource.Success(busLinesFromDB))
        } else {
            try {
                val busLinesFromAPI = api.getBusLines()
                lineDao.clearBusLines()
                lineDao.insertBusLines(busLinesFromAPI.map { it.toBusLineEntity() })
                val busLineCodes = busLinesFromAPI.map { it.LineCode }
                emit(Resource.Success(busLineCodes))

            } catch (e: HttpException) {
                emit(Resource.Error(message = e.localizedMessage ?: "error"))
            } catch (e: Exception) {
                emit(Resource.Error(message = e.message ?: "An error occurred"))
            }
        }
    }

    override fun getLinesFromLineID(lineID: String): Flow<Resource<List<Line>>> = flow {
        emit(Resource.Loading())

        try {

            val availableLines = lineDao.getLinesFromLineID(query = lineID).map { it.toBusLine() }

            emit(Resource.Success(availableLines))

        } catch (e: HttpException) {
            emit(Resource.Error(message = e.localizedMessage ?: "error"))
        } catch (e: Exception) {
            emit(Resource.Error(message = e.message ?: "An error occurred"))
        }
    }

    override suspend fun toggleFavoriteLine(lineID: String) {

        lineDao.toggleFavoriteLine(lineID)

    }

    override fun getStopsFromXY(latitude: String, longitude: String): Flow<Resource<List<Stop>>> =
        flow {
            emit(Resource.Loading())
            try {
                val closestStopsRemote = api.getClosestStops(x = latitude, y = longitude)
                val closestStops = closestStopsRemote.map { it.toStop() }
                emit(Resource.Success(closestStops))
            } catch (e: HttpException) {
                emit(Resource.Error(message = e.localizedMessage ?: "error"))
            } catch (e: Exception) {
                emit(Resource.Error(message = e.message ?: "An error occurred"))
            }
        }

    override fun getStopArrivals(stopCode: String): Flow<Resource<List<Arrival>>> = flow {
        emit(Resource.Loading())
        try {
            val arrivals = api.getStopArrivals(stopCode = stopCode)?.map { it.toArrival() }
            emit(Resource.Success(data = arrivals ?: emptyList()))
        } catch (e: HttpException) {
            emit(Resource.Error(message = e.localizedMessage ?: "error"))
        } catch (e: Exception) {
            emit(Resource.Error(message = e.message ?: "An error occurred"))
        }
    }

    override fun getRoutesForStop(stopCode: String): Flow<Resource<List<Route>>> = flow {
        emit(Resource.Loading())
        try {
            val arrivals = api.webRoutesForStop(stopCode = stopCode).map { it.toRoute() }
            emit(Resource.Success(data = arrivals))
        } catch (e: HttpException) {
            emit(Resource.Error(message = e.localizedMessage ?: "error"))
        } catch (e: Exception) {
            emit(Resource.Error(message = e.message ?: "An error occurred"))
        }
    }

    override fun getStopsFromRoute(routeCode: String): Flow<Resource<List<Stop>>> = flow {
        emit(Resource.Loading())
        try {
            val stops = api.getStopsForRoute(routeCode = routeCode).map { it.toStop() }
            emit(Resource.Success(data = stops))
        } catch (e: HttpException) {
            emit(Resource.Error(message = e.localizedMessage ?: "error"))
        } catch (e: Exception) {
            emit(Resource.Error(message = e.message ?: "An error occurred"))
        }
    }

    override fun getDailySchedules(lineCode: String): Flow<Resource<DailySchedule>> = flow {
        emit(Resource.Loading())
        try {
            val schedule = api.getDailySchedule(lineCode = lineCode).toDailySchedule()
            emit(Resource.Success(data = schedule))
        } catch (e: HttpException) {
            emit(Resource.Error(message = e.localizedMessage ?: "error"))
        } catch (e: Exception) {
            emit(Resource.Error(message = e.message ?: "An error occurred"))
        }
    }

    override fun getFavoriteLines(): Flow<Resource<List<Line>>> = flow {
        emit(Resource.Loading())
        try {
            val favorites = favoritesDao.getFavoriteLines().map { it.toBusLine() }
            emit(Resource.Success(data = favorites))
        } catch (e: Exception) {
            emit(Resource.Error(message = e.message ?: "An error occurred"))
        }
    }

    override suspend fun addFavoriteLine(line: Line) {
        favoritesDao.insertFavoriteLine(line.toFavoriteLineEntity())
    }

    override suspend fun isFavoriteLine(line: String): Boolean {
        return favoritesDao.checkFavoriteLine(line) != 0
    }

    override suspend fun removeFavoriteLine(line: String) {
        favoritesDao.deleteFavoriteFromID(line)
    }

    override fun getStopDetailsFromCode(stopCode: String): Flow<Resource<Stop>> = flow {
        emit(Resource.Loading())
        try {
            val stop = api.getStopNameAndXY(stopCode = stopCode).first().toStop(stopCode)
            emit(Resource.Success(data = stop))
        } catch (e: HttpException) {
            emit(Resource.Error(message = e.localizedMessage ?: "error"))
        } catch (e: Exception) {
            emit(Resource.Error(message = e.message ?: "An error occurred"))
        }
    }
}
