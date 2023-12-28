package com.example.athenabus.presentation.favorites

import com.example.athenabus.domain.model.Route
import com.example.athenabus.domain.model.Stop

data class FavoriteStopsRouteState(
    val isLoading: Boolean = false,
    val error: String = "",
    val isRefreshing: Boolean = false,
    val routes: List<Route> = emptyList(),
    val routesForStops: HashMap<String, List<Route>> = HashMap(),
)