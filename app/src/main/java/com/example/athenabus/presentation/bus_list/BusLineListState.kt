package com.example.athenabus.presentation.bus_list

import com.example.athenabus.domain.model.Line
import com.example.athenabus.domain.model.Route

data class BusLineListState(
    val isLoading: Boolean = false,
    val busLines: List<Line> = emptyList(),
    val lineRoutes: List<Route> = emptyList(),
    val error: String = "",
    val isRefreshing: Boolean = false,
    var searchQuery: String = ""
)
