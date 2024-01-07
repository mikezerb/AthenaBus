package com.example.athenabus.domain.use_case.bus_lines

import com.example.athenabus.common.Resource
import com.example.athenabus.domain.model.BusLocation
import com.example.athenabus.domain.repository.BusLineRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetBusLocationsUseCase @Inject constructor(
    private val repository: BusLineRepository,
) {

    operator fun invoke(routeCode: String): Flow<Resource<List<BusLocation>>> {
        return repository.getBusLocation(routeCode)
    }

}