package com.example.athenabus.presentation.closest_stops

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.athenabus.common.Resource
import com.example.athenabus.domain.model.Arrival
import com.example.athenabus.domain.use_case.bus_lines.GetStopArrivalUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ArrivalsForStopViewModel @Inject constructor(
    private val getStopArrivalUseCase: GetStopArrivalUseCase
) : ViewModel() {

    private val _state = mutableStateOf(ArrivalsForStopState())
    val state: State<ArrivalsForStopState> = _state

    val arrivalsForStop: HashMap<String, List<Arrival>> = HashMap()

    fun getStopArrival(stopCode: String) {
        if (arrivalsForStop.containsKey(stopCode)) {
            Log.d(
                "getStopArrival", " // Cache the fetched routes in the HashMap.\n" +
                        "                        routesForStops[stopCode] = routes containsKey : $stopCode"
            )
            _state.value =
                ArrivalsForStopState(arrivals = arrivalsForStop[stopCode] ?: emptyList())
        }
        getStopArrivalUseCase(stopCode).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    Log.d(
                        "getStopArrival",
                        "For stop: $stopCode got " + result.data?.size + " closestStopsArrival"
                    )
                    val hash = hashMapOf(stopCode to (result.data ?: emptyList()))
                    Log.d(
                        "getStopArrival",
                        "keys: " + state.value.arrivalsForStop.keys.toString() + " values: " +
                                state.value.arrivalsForStop.values.size.toString()
                    )
                    // Cache the fetched routes in the HashMap.
                    arrivalsForStop[stopCode] = result.data ?: emptyList()
                    _state.value = ArrivalsForStopState(arrivalsForStop = hash)
                }

                is Resource.Error -> {
                    Log.e(
                        "getStopArrivalUseCase",
                        "getStopArrivalUseCase" + "stop code: " + stopCode + result.message
                    )
                    _state.value =
                        ArrivalsForStopState(error = result.message ?: "Unexpected error")
                }

                is Resource.Loading -> {
                    _state.value = ArrivalsForStopState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }
}