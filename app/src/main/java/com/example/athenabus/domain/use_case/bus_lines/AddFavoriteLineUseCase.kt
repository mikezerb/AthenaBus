package com.example.athenabus.domain.use_case.bus_lines

import com.example.athenabus.domain.model.Line
import com.example.athenabus.domain.repository.BusLineRepository
import javax.inject.Inject

class AddFavoriteLineUseCase @Inject constructor(
    private val repository: BusLineRepository,
) {

    suspend operator fun invoke(line: Line) {
        return repository.addFavoriteLine(line)
    }

}