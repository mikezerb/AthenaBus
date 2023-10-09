package com.example.athenabus.presentation.closest_stops

import com.example.athenabus.domain.model.Arrival
import com.example.athenabus.domain.model.Stop

data class ClosestStopsState(
    val isLoading: Boolean = false,
    val error: String = "",
    val isRefreshing: Boolean = false,
    val closestStops: List<Stop> = emptyList(),
    val closestStopsArrival: List<Arrival> = emptyList()
)
