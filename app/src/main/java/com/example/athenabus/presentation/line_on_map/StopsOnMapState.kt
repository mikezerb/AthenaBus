package com.example.athenabus.presentation.line_on_map

import com.example.athenabus.domain.model.Stop

data class StopsOnMapState(
    val isLoading: Boolean = false,
    val error: String = "",
    val routeStops: List<Stop> = emptyList(),
)
