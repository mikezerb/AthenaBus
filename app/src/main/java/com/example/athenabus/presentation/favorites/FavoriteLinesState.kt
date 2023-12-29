package com.example.athenabus.presentation.favorites

import com.example.athenabus.domain.model.Line

data class FavoriteLinesState(
    val isLoading: Boolean = false,
    val error: String = "",
    val isRefreshing: Boolean = false,
    val favoriteLines: List<Line> = emptyList(),
)