package com.example.athenabus.presentation.closest_stops

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.athenabus.common.Resource
import com.example.athenabus.domain.use_case.bus_lines.GetStopArrivalUseCase
import com.example.athenabus.domain.use_case.bus_lines.GetStopsFromLocation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject


private const val TAG = "ClosestStopsViewModel"

@HiltViewModel
class ClosestStopsViewModel @Inject constructor(
    private val getStopsFromLocation: GetStopsFromLocation,
    private val getStopArrivalUseCase: GetStopArrivalUseCase,
) : ViewModel() {

    private val _state = mutableStateOf(ClosestStopsState())
    val state: State<ClosestStopsState> = _state

    fun getClosestStops(x: String, y: String) {
        getStopsFromLocation(x, y).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = ClosestStopsState(closestStops = result.data ?: emptyList())
                }

                is Resource.Error -> {
                    Log.e(TAG, "getStopsFromLocation error: " + result.message)
                    _state.value =
                        ClosestStopsState(error = result.message ?: "Unexpected error")
                }

                is Resource.Loading -> {
                    _state.value = ClosestStopsState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun getStopArrival(stopCode: String) {
        getStopArrivalUseCase(stopCode).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    Log.d(TAG, "Got " + result.data?.size + " closestStopsArrival")
                    _state.value =
                        ClosestStopsState(closestStopsArrival = result.data ?: emptyList())
                }

                is Resource.Error -> {
                    Log.e(
                        TAG,
                        "getStopArrivalUseCase" + "stop code: " + stopCode + result.message
                    )
                    _state.value =
                        ClosestStopsState(error = result.message ?: "Unexpected error")
                }

                is Resource.Loading -> {
                    _state.value = ClosestStopsState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }
}
