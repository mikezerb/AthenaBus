package com.example.athenabus.presentation.line_on_map

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.athenabus.common.Constants
import com.example.athenabus.common.Resource
import com.example.athenabus.domain.use_case.bus_lines.GetBusLocationsUseCase
import com.example.athenabus.domain.use_case.get_route.GetRouteFromLineIdUseCase
import com.example.athenabus.domain.use_case.get_stops.GetStopsFromRouteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class LinesOnMapViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getBusLocationsUseCase: GetBusLocationsUseCase,
    private val getStopsFromRouteUseCase: GetStopsFromRouteUseCase,
    private val getRouteFromLineIdUseCase: GetRouteFromLineIdUseCase

) : ViewModel() {

    private val _state = mutableStateOf(LinesOnMapState())
    val state: State<LinesOnMapState> = _state

    private val _stopsState = mutableStateOf(StopsOnMapState())
    val stopsState: State<StopsOnMapState> = _stopsState

    private val _routesState = mutableStateOf(RoutesState())
    val routesState: State<RoutesState> = _routesState

    init {
        savedStateHandle.get<String>(Constants.PARAM_LINE_ID)?.let { lineId ->
            getRoutesFromLineId(lineId)
        }
    }

    fun getRoutesFromLineId(lineId: String) {
        getRouteFromLineIdUseCase(lineId).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _routesState.value =
                        RoutesState(routes = result.data ?: emptyList())
                }

                is Resource.Error -> {
                    _routesState.value =
                        RoutesState(error = result.message ?: "Unexpected error")
                }

                is Resource.Loading -> {
                    _routesState.value = RoutesState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)

    }

    fun updateData(routeCode: String) {
        getBusLocations(routeCode)
        getStopsFromRoute(routeCode)
    }

    private fun getBusLocations(routeCode: String) {
        getBusLocationsUseCase(routeCode).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    val busLocations = result.data
                    _state.value = LinesOnMapState(busLocations = busLocations ?: emptyList())
                }

                is Resource.Error -> {
                    _state.value = LinesOnMapState(error = result.message ?: "Unexpected error")
                }

                is Resource.Loading -> {
                    _state.value = LinesOnMapState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun getStopsFromRoute(routeCode: String) {
        getStopsFromRouteUseCase(routeCode).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    val routeStops = result.data
                    _stopsState.value = StopsOnMapState(routeStops = routeStops ?: emptyList())
                }

                is Resource.Error -> {
                    _stopsState.value =
                        StopsOnMapState(error = result.message ?: "Unexpected error")
                }

                is Resource.Loading -> {
                    _stopsState.value = StopsOnMapState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

}