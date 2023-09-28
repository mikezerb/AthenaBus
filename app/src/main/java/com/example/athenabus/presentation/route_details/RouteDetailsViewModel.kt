package com.example.athenabus.presentation.route_details

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.athenabus.domain.repository.RouteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RouteDetailsViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val repository: RouteRepository
) : ViewModel() {

    var state by mutableStateOf(RouteDetailsState())

    init {
        viewModelScope.launch {
            state = state.copy(isLoading = true)
            val routeCode = "2045"

            val routeStops = async { repository.getStopsForRoute(routeCode) }
            when (val res = routeStops.await()) {


            }

        }
    }
}