package com.example.athenabus.presentation.favorites.tabs

import com.example.athenabus.domain.model.Line

data class FavoriteLinesStopsState(
    val isLoading: Boolean = false,
    val error: String = "",
    val isRefreshing: Boolean = false,
    val favoriteLines: List<Line> = emptyList(),
    val favoriteStops: List<Line> = emptyList(),
)