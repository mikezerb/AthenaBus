package com.example.athenabus.presentation.closest_stops

import android.location.Location

data class LocationState(
    var currentLocation: Location? = null,
    val isLoading: Boolean = false,
    val error: String = "",
    val isRefreshing: Boolean = false,
    val isRequestPermission: Boolean = false,
    val isLocationEnabled: Boolean = false,
    val isNetworkEnabled: Boolean = false
)
