package com.example.athenabus.domain.use_case.get_bus_lines

import com.example.athenabus.common.Resource
import com.example.athenabus.data.remote.dto.toLine
import com.example.athenabus.domain.model.Line
import com.example.athenabus.domain.repository.LineRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class getLinesUseCase @Inject constructor(
    private val repository: LineRepository
){
    operator fun invoke(): Flow<Resource<List<Line>>> = flow {
        try {
            emit(Resource.Loading<List<Line>>())
            val bus_lines = repository.getLines().map { it.toLine() }
            emit(Resource.Success<List<Line>>(bus_lines))
        }catch (e: HttpException){
            emit(Resource.Error<List<Line>>(e.localizedMessage ?: "error"))

        }catch (e: IOException){
            emit(Resource.Error<List<Line>>("Internet connection error"))
        }
    }
}