package com.example.athenabus.domain.use_case.get_route

import com.example.athenabus.common.Resource
import com.example.athenabus.domain.model.Route
import com.example.athenabus.domain.repository.BusLineRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetRoutesForStopsUseCase @Inject constructor(
    private val repository: BusLineRepository
) {

    operator fun invoke(stopCode: String): Flow<Resource<List<Route>>> {
        return repository.getRoutesForStop(stopCode)
    }
}