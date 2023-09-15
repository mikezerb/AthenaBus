package com.example.athenabus.domain.use_case.get_bus_lines

import com.example.athenabus.common.Resource
import com.example.athenabus.domain.model.Line
import com.example.athenabus.domain.repository.BusLineRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class getLinesUseCase @Inject constructor(
    private val repository: BusLineRepository,
) {

    operator fun invoke(): Flow<Resource<List<Line>>> {
        return repository.getBusLines()
    }

}