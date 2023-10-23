package com.example.athenabus.data.repository

import com.example.athenabus.common.Resource
import com.example.athenabus.data.local.TelematicsLineDao
import com.example.athenabus.data.mapper.ToStop
import com.example.athenabus.data.mapper.toArrival
import com.example.athenabus.data.mapper.toBusLine
import com.example.athenabus.data.mapper.toBusLineEntity
import com.example.athenabus.data.mapper.toRoute
import com.example.athenabus.data.mapper.toStop
import com.example.athenabus.data.remote.OASATelematicsAPI
import com.example.athenabus.domain.model.Arrival
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
) : BusLineRepository {
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
            val arrivals = api.getStopArrivals(stopCode = stopCode).map { it.toArrival() }
            emit(Resource.Success(data = arrivals))
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
            val stops = api.getStopsForRoute(routeCode = routeCode).map { it.ToStop() }
            emit(Resource.Success(data = stops))
        } catch (e: HttpException) {
            emit(Resource.Error(message = e.localizedMessage ?: "error"))
        } catch (e: Exception) {
            emit(Resource.Error(message = e.message ?: "An error occurred"))
        }
    }
}
