package com.example.athenabus.presentation.stop_arrival

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.athenabus.common.Resource
import com.example.athenabus.domain.use_case.bus_lines.GetStopArrivalUseCase
import com.example.athenabus.domain.use_case.get_route.GetRoutesForStopsUseCase
import com.example.athenabus.domain.use_case.get_stops.GetStopDetailsFromCodeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class StopArrivalViewModel @Inject constructor(
    private val getStopDetailsFromCodeUseCase: GetStopDetailsFromCodeUseCase,
    private val getStopArrivalUseCase: GetStopArrivalUseCase,
    private val getRoutesForStopsUseCase: GetRoutesForStopsUseCase,
) : ViewModel() {

    private val _state = mutableStateOf(StopArrivalState())
    val state: State<StopArrivalState> = _state

    fun getStopDetails(stopCode: String) {
        getStopDetailsFromCodeUseCase(stopCode).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    val busLines = result.data
                    _state.value = StopArrivalState(busStop = busLines)
                }

                is Resource.Error -> {
                    _state.value = StopArrivalState(error = result.message ?: "Unexpected error")
                }

                is Resource.Loading -> {
                    _state.value = StopArrivalState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun getStopArrivals(stopCode: String) {
        getStopArrivalUseCase(stopCode).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    val arrivals = result.data
                    Log.d("getStopArrivals", "arrivals: ${arrivals?.size}")
                    _state.value = StopArrivalState(stopArrivals = arrivals ?: emptyList())
                }

                is Resource.Error -> {
                    Log.d("getStopArrivals", "error: ${result.message ?: "Unexpected error"}")
                    _state.value = StopArrivalState(error = result.message ?: "Unexpected error")
                }

                is Resource.Loading -> {
                    _state.value = StopArrivalState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun getRoutesForStop(stopCode: String) {
        getRoutesForStopsUseCase(stopCode).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    val routes = result.data
                    Log.d("getStopArrivals", "routes: ${routes?.size}")
                    _state.value = StopArrivalState(routeStops = routes ?: emptyList())
                }

                is Resource.Error -> {
                    Log.d("getStopArrivals", "error: ${result.message ?: "Unexpected error"}")
                    _state.value = StopArrivalState(error = result.message ?: "Unexpected error")
                }

                is Resource.Loading -> {
                    _state.value = StopArrivalState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

}