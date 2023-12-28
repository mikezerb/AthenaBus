package com.example.athenabus.presentation.stop_arrival

import com.example.athenabus.domain.model.Arrival
import com.example.athenabus.domain.model.Route
import com.example.athenabus.domain.model.Stop

data class StopArrivalState(
    val isLoading: Boolean = false,
    val stopArrivals: List<Arrival> = emptyList(),
    val error: String = "",
    val isRefreshing: Boolean = false,
)
