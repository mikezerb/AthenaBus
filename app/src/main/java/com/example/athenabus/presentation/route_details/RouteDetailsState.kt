package com.example.athenabus.presentation.route_details

import com.example.athenabus.domain.model.Route
import com.example.athenabus.domain.model.Stop

data class RouteDetailsState(
    val availableRoutes: List<Route> = emptyList(),
    val routeStops: List<Stop> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)
