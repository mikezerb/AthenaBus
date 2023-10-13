package com.example.athenabus.domain.use_case.get_route

import com.example.athenabus.common.Resource
import com.example.athenabus.domain.model.Route
import com.example.athenabus.domain.repository.RouteRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetRouteFromLineIdUseCase @Inject constructor(
    private val repository: RouteRepository
) {

    operator fun invoke(lineId: String): Flow<Resource<List<Route>>> {
        return repository.getRoutesByID(lineId)
    }
}
