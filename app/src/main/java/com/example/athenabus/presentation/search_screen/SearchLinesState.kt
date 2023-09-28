package com.example.athenabus.presentation.search_screen

import com.example.athenabus.domain.model.Line

data class SearchLinesState(
    val lines: List<Line> = emptyList(),
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    var searchQuery: String = ""
)
