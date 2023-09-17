package com.example.athenabus.domain.repository

import com.example.athenabus.common.Resource
import com.example.athenabus.domain.model.Route
import com.example.athenabus.domain.model.Stop
import kotlinx.coroutines.flow.Flow

interface RouteRepository {
    fun getRoutesByID(lineID: String): Flow<Resource<List<Route>>>

    fun getStopsForRoute(routeCode: String): Flow<Resource<List<Stop>>>
}