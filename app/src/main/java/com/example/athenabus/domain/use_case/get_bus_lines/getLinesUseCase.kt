package com.example.athenabus.domain.use_case.get_bus_lines

import com.example.athenabus.common.Resource
import com.example.athenabus.domain.model.Line
import com.example.athenabus.domain.repository.BusLineRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class getLinesUseCase @Inject constructor(
    private val repository: BusLineRepository,
){

    operator fun invoke(): Flow<Resource<List<Line>>> {
        return repository.getBusLines()
    }

    /*
    operator fun invoke(): Flow<Resource<List<Line>>> = flow {
        try {
            emit(Resource.Loading<List<Line>>())

            val busLines = repository.getLines().map { it.toLine() }

            // Filter the bus lines to get distinct LineIDs
            val distinctBusLines = busLines.distinctBy { it.LineID }

            emit(Resource.Success<List<Line>>(distinctBusLines))
        }catch (e: HttpException) {
            emit(Resource.Error<List<Line>>(e.localizedMessage ?: "error"))
        }catch (e: IOException) {
            emit(Resource.Error<List<Line>>("Internet connection error"))
        }
    }
     */
}