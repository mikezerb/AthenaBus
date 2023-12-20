package com.example.athenabus.presentation.favorites.tabs

import com.example.athenabus.domain.model.Stop

data class FavoriteStopsState(
    val isLoading: Boolean = false,
    val error: String = "",
    val isRefreshing: Boolean = false,
    val favoriteStops: List<Stop> = emptyList(),
)