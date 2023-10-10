package com.example.athenabus.presentation.closest_stops

import com.example.athenabus.domain.model.Arrival

data class ArrivalsForStopState(
    val isLoading: Boolean = false,
    val error: String = "",
    val isRefreshing: Boolean = false,
    val arrivals: List<Arrival> = emptyList(),
    val arrivalsForStop: HashMap<String, List<Arrival>> = hashMapOf()
)