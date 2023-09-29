package com.example.athenabus.data.repository

import com.example.athenabus.common.Resource
import com.example.athenabus.data.local.TelematicsLineDao
import com.example.athenabus.data.local.TelematicsRouteDao
import com.example.athenabus.data.mapper.toBusLine
import com.example.athenabus.data.mapper.toBusLineEntity
import com.example.athenabus.data.remote.OASATelematicsAPI
import com.example.athenabus.domain.model.Line
import com.example.athenabus.domain.repository.BusLineRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class BusLineRepositoryImpl(
    private val api: OASATelematicsAPI,
    private val lineDao: TelematicsLineDao,
    private val routeDao: TelematicsRouteDao
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

    override fun getLineFromDB(searchTxt: String): Flow<Resource<List<Line>>> = flow {
        emit(Resource.Loading())

        val busLines = lineDao.searchBusLines(searchTxt).map { it.toBusLine() }
        emit(Resource.Loading(data = busLines))

        val isDbEmpty = busLines.isEmpty() && searchTxt.isBlank()

        if (!isDbEmpty) {
            // Filter the bus lines to get distinct LineIDs
            val distinctBusLines = busLines.distinctBy { it.LineID }
            emit(Resource.Success(distinctBusLines))
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

    override fun addFavoriteLines(line: Line) {

    }
}