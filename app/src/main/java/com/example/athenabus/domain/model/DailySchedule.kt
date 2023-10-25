package com.example.athenabus.domain.model

data class DailySchedule(
    val come: List<ComeUI>,
    val go: List<GoUI>
)

data class ComeUI(
    val time: String
)

data class GoUI(
    val time: String
)