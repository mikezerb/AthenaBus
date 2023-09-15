package com.example.athenabus.presentation.bus_detail

import com.example.athenabus.domain.model.Route

data class RouteListState(
    val isLoading: Boolean = false,
    val busRoutes: List<Route> = emptyList(),
    val error: String = ""
)
