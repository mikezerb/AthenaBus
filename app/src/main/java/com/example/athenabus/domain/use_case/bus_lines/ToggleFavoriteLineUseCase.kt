package com.example.athenabus.domain.use_case.bus_lines

import com.example.athenabus.domain.repository.BusLineRepository
import javax.inject.Inject

class ToggleFavoriteLineUseCase @Inject constructor(
    private val repository: BusLineRepository,
) {

    suspend operator fun invoke(lineID: String) {
        return repository.toggleFavoriteLine(lineID = lineID)
    }

}