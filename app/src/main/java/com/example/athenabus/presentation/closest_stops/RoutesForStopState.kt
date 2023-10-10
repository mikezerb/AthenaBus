package com.example.athenabus.presentation.closest_stops

import com.example.athenabus.domain.model.Route

data class RoutesForStopState(
    val isLoading: Boolean = false,
    val error: String = "",
    val isRefreshing: Boolean = false,
    val routesForStop: List<Route> = emptyList()
)
