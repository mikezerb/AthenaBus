package com.example.athenabus.domain.use_case.bus_lines

import com.example.athenabus.common.Resource
import com.example.athenabus.domain.model.Line
import com.example.athenabus.domain.repository.BusLineRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetLineFromIDUseCase @Inject constructor(
    private val repository: BusLineRepository,
) {

    operator fun invoke(lineId: String): Flow<Resource<Line>> {
        return repository.getLineFromDB(lineId)
    }
}
