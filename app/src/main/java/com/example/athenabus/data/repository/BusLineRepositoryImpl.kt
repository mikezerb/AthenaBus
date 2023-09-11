package com.example.athenabus.data.repository

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
): BusLineRepository {
    override fun getBusLines(): Flow<Resource<List<Line>>>  = flow {
        emit(Resource.Loading())

        val busLines = dao.getBusLines().map { it.toBusLine() }
        emit(Resource.Loading(data = busLines))

        try {

            val remoteBusLines = api.getBusLines()
            dao.clearBusLines()
            dao.insertBusLines(remoteBusLines.map { it.toBusLineEntity() })

        }catch (e: HttpException){
            emit(Resource.Error(
                message = e.localizedMessage ?: "error",
                data = busLines)
            )
        } catch (e: IOException){
            emit(Resource.Error(
                message ="Internet connection error",
                data = busLines)
            )
        }
        val newBusLines = dao.getBusLines().map { it.toBusLine() }
        // Filter the bus lines to get distinct LineIDs
        val distinctBusLines = newBusLines.distinctBy { it.LineID }

        emit(Resource.Success(distinctBusLines))
    }
}