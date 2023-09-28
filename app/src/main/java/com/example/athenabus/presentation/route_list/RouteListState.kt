package com.example.athenabus.presentation.route_list

import com.example.athenabus.domain.model.Route

data class RouteListState(
    val isLoading: Boolean = false,
    val busRoutes: List<Route> = emptyList(),
    val bus_codes: List<String> = emptyList(),
    val error: String = ""
)
