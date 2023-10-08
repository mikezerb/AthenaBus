package com.example.athenabus.domain.repository

import android.location.Location
import com.example.athenabus.common.Resource
import kotlinx.coroutines.flow.Flow

interface LocationRepository {

    fun getCurrentLocation(): Flow<Resource<Location>>

    class LocationException(message: String) : Exception(message)

}