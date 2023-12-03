package com.example.athenabus.presentation.stop_arrival

import com.example.athenabus.domain.model.Route
import com.example.athenabus.domain.model.Stop

data class StopState(
    val isLoading: Boolean = false,
    val busStop: Stop? = null,
    val routeStops: List<Route> = emptyList(),
    val error: String = "",
    val isRefreshing: Boolean = false,
)
