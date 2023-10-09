package com.example.athenabus.domain.use_case.bus_lines

import com.example.athenabus.common.Resource
import com.example.athenabus.domain.model.Arrival
import com.example.athenabus.domain.repository.BusLineRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetStopArrivalUseCase @Inject constructor(
    private val repository: BusLineRepository,
) {

    operator fun invoke(stopCode: String): Flow<Resource<List<Arrival>>> {
        return repository.getStopArrivals(stopCode)
    }

}