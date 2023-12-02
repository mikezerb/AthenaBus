package com.example.athenabus.presentation.stop_arrival

import com.example.athenabus.domain.model.Stop

data class StopArrivalState(
    val isLoading: Boolean = false,
    val busStop: Stop? = null,
    val error: String = "",
    val isRefreshing: Boolean = false,
    var searchQuery: String = ""
)
