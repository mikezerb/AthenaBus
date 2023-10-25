package com.example.athenabus.presentation.line_details

import com.example.athenabus.domain.model.DailySchedule

data class RouteScheduleState(
    val schedules: DailySchedule? = null,
    val error: String = "",
    val isLoading: Boolean = false,
)
