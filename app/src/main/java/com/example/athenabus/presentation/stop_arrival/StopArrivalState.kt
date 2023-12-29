package com.example.athenabus.presentation.stop_arrival

import com.example.athenabus.domain.model.Arrival

data class StopArrivalState(
    val isLoading: Boolean = false,
    val stopArrivals: List<Arrival> = emptyList(),
    val error: String = "",
    val isRefreshing: Boolean = false,
)
