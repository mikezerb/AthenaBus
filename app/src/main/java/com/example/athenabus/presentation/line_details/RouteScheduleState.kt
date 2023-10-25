package com.example.athenabus.presentation.line_details

import com.example.athenabus.domain.model.Stop

data class RouteScheduleState(
    val stops: List<Stop> = emptyList(),
    val error: String = "",
    val isLoading: Boolean = false,
)
