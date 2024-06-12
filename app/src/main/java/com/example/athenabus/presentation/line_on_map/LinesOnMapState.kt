package com.example.athenabus.presentation.line_on_map

import com.example.athenabus.domain.model.BusLocation
import com.example.athenabus.domain.model.Stop

data class LinesOnMapState(
    val isLoading: Boolean = false,
    val error: String = "",
    val busLocations: List<BusLocation> = emptyList(),
    val routeStops: List<Stop> = emptyList(),
)
