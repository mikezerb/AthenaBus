package com.example.athenabus.domain.use_case.get_stops

import com.example.athenabus.domain.repository.BusLineRepository
import javax.inject.Inject

class RemoveFavoriteStopUseCase @Inject constructor(
    private val repository: BusLineRepository,
) {

    suspend operator fun invoke(stop: String) {
        return repository.removeFavoriteStop(stop)
    }

}