package com.example.athenabus.domain.use_case.bus_lines

import com.example.athenabus.common.Resource
import com.example.athenabus.domain.repository.BusLineRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetBusCodesUseCase @Inject constructor(
    private val repository: BusLineRepository,
) {

    operator fun invoke(lineId: String): Flow<Resource<List<String>>> {
        return repository.getLineCodesFromLineID(lineId)
    }

}