package com.example.athenabus.domain.use_case.get_stops

import com.example.athenabus.domain.model.Stop
import com.example.athenabus.domain.repository.BusLineRepository
import javax.inject.Inject

class AddFavoriteStopUseCase @Inject constructor(
    private val repository: BusLineRepository,
) {

    suspend operator fun invoke(stop: Stop) {
        return repository.addFavoriteStop(stop)
    }

}