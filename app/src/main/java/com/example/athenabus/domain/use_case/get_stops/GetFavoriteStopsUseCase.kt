package com.example.athenabus.domain.use_case.get_stops

import com.example.athenabus.common.Resource
import com.example.athenabus.domain.model.Stop
import com.example.athenabus.domain.repository.BusLineRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFavoriteStopsUseCase @Inject constructor(
    private val repository: BusLineRepository,
) {

    operator fun invoke(): Flow<Resource<List<Stop>>> {
        return repository.getFavoriteStops()
    }
}