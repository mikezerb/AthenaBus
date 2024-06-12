package com.example.athenabus.presentation.line_on_map

import com.example.athenabus.domain.model.Route

data class RoutesState(
    val isLoading: Boolean = false,
    val error: String = "",
    val routes: List<Route> = emptyList(),
)
