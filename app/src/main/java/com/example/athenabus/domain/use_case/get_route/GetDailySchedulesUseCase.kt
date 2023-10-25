package com.example.athenabus.domain.use_case.get_route

import com.example.athenabus.common.Resource
import com.example.athenabus.domain.model.DailySchedule
import com.example.athenabus.domain.repository.BusLineRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetDailySchedulesUseCase @Inject constructor(
    private val repository: BusLineRepository
) {

    operator fun invoke(lineCode: String): Flow<Resource<DailySchedule>> {
        return repository.getDailySchedules(lineCode)
    }
}