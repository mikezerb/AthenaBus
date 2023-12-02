package com.example.athenabus.presentation.stop_arrival

import com.example.athenabus.domain.model.Arrival
import com.example.athenabus.domain.model.Route
import com.example.athenabus.domain.model.Stop

data class StopArrivalState(
    val isLoading: Boolean = false,
    val busStop: Stop? = null,
    val stopArrivals: List<Arrival> = emptyList(),
    val routeStops: List<Route> = emptyList(),
    val error: String = "",
    val isRefreshing: Boolean = false,
)
