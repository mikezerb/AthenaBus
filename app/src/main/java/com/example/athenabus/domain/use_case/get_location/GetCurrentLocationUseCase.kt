package com.example.athenabus.domain.use_case.get_location

import android.location.Location
import com.example.athenabus.common.Resource
import com.example.athenabus.domain.repository.LocationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCurrentLocationUseCase @Inject constructor(
    private val repository: LocationRepository,
) {
    operator fun invoke(): Flow<Resource<Location>> {
        return repository.getCurrentLocation()
    }
}