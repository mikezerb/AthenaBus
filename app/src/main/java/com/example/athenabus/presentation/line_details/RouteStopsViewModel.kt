package com.example.athenabus.presentation.line_details

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.athenabus.common.Resource
import com.example.athenabus.domain.use_case.get_stops.GetStopsFromRouteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class RouteStopsViewModel @Inject constructor(
    private val getStopsFromRouteUseCase: GetStopsFromRouteUseCase
) : ViewModel() {

    private val _state = mutableStateOf(RouteStopsState())
    val state: State<RouteStopsState> = _state

    fun getStops(routeCode: String) {
        getStopsFromRouteUseCase(routeCode).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    val stops = result.data ?: emptyList()
                    _state.value = RouteStopsState(stops = stops)
                }

                is Resource.Error -> {
                    _state.value = RouteStopsState(error = result.message ?: "Unexpected error")
                }

                is Resource.Loading -> {
                    _state.value = RouteStopsState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }
}