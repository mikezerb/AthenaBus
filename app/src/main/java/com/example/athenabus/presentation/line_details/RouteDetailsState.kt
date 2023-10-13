package com.example.athenabus.presentation.line_details

import com.example.athenabus.domain.model.Route
import com.example.athenabus.domain.model.Stop

data class RouteDetailsState(
    val availableRoutes: List<Route> = emptyList(),
    val error: String = "",
    val routeStops: List<Stop> = emptyList(),
    val isLoading: Boolean = false,
)
