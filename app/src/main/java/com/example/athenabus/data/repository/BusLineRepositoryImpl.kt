package com.example.athenabus.data.repository

import android.util.Log
import com.example.athenabus.common.Resource
import com.example.athenabus.data.local.TelematicsDao
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
    private val dao: TelematicsDao
) : BusLineRepository {
    override fun getBusLines(): Flow<Resource<List<Line>>> = flow {
        emit(Resource.Loading())

        var busLines = dao.getBusLines().map { it.toBusLine() }

        if (busLines.isEmpty()) {
            try {
                val remoteBusLines = api.getBusLines()
                dao.clearBusLines()
                dao.insertBusLines(remoteBusLines.map { it.toBusLineEntity() })

                busLines = dao.getBusLines().map { it.toBusLine() }
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

        val busLines = dao.searchBusLines(searchTxt).map { it.toBusLine() }
        emit(Resource.Loading(data = busLines))

        val isDbEmpty = busLines.isEmpty() && searchTxt.isBlank()

        if (!isDbEmpty) {
            // Filter the bus lines to get distinct LineIDs
            val distinctBusLines = busLines.distinctBy { it.LineID }
            emit(Resource.Success(distinctBusLines))
        }

        var remoteBusList = try {
            val remoteBusLines = api.getBusLines()
            dao.clearBusLines()
            dao.insertBusLines(remoteBusLines.map { it.toBusLineEntity() })
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

        val busLinesFromDB = dao.getLineCodesFromLineID(lineID)

        if (busLinesFromDB.isNotEmpty()) {
            Log.d("bus_codes", busLinesFromDB.toString())
            emit(Resource.Success(busLinesFromDB))
        } else {
            try {
                val BusLinesFromAPI = api.getBusLines()
                dao.clearBusLines()
                dao.insertBusLines(BusLinesFromAPI.map { it.toBusLineEntity() })
                val busLineCodes = dao.getLineCodesFromLineID(lineID)
                Log.d("bus_codes", busLineCodes.toString())

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