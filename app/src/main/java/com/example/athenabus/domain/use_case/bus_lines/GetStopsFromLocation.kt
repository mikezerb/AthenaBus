package com.example.athenabus.domain.use_case.bus_lines

import com.example.athenabus.common.Resource
import com.example.athenabus.domain.model.Stop
import com.example.athenabus.domain.repository.BusLineRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetStopsFromLocation @Inject constructor(
    private val repository: BusLineRepository,
) {

    operator fun invoke(x: String, y: String): Flow<Resource<List<Stop>>> {
        return repository.getStopsFromXY(x, y)
    }

}