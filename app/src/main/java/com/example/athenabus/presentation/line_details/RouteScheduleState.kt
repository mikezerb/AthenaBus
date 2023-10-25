package com.example.athenabus.presentation.line_details

import com.example.athenabus.domain.model.DailySchedule
import com.example.athenabus.domain.model.Line

data class RouteScheduleState(
    val schedules: DailySchedule? = null,
    val availableLines: List<Line> = emptyList(),
    val error: String = "",
    val isLoading: Boolean = false,
)
