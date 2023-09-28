package com.example.athenabus.presentation.bus_list

import com.example.athenabus.domain.model.Line

data class BusLineListState(
    val isLoading: Boolean = false,
    val bus_lines: List<Line> = emptyList(),
    val error: String = ""
)
