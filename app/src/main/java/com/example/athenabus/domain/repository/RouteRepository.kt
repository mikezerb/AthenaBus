package com.example.athenabus.domain.repository

import com.example.athenabus.common.Resource
import com.example.athenabus.domain.model.Route
import kotlinx.coroutines.flow.Flow

interface RouteRepository {
    fun getRoutesByID(lineID: String): Flow<Resource<List<Route>>>
}