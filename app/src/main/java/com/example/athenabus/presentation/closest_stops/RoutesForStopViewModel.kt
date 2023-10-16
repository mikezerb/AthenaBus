package com.example.athenabus.presentation.closest_stops

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.athenabus.common.Resource
import com.example.athenabus.domain.model.Route
import com.example.athenabus.domain.use_case.get_route.GetRoutesForStopsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

private const val TAG = "RoutesForStopViewModel"

@HiltViewModel
class RoutesForStopViewModel @Inject constructor(
    private val getRoutesForStopsUseCase: GetRoutesForStopsUseCase,
) : ViewModel() {
    private val _state = mutableStateOf(RoutesForStopState())
    val state: State<RoutesForStopState> = _state

    val routesForStops: HashMap<String, List<Route>> = HashMap()

    fun getRoutesForStop(stopCode: String) {
        if (routesForStops.containsKey(stopCode)) {
            Log.d(TAG, "routesForStops containsKey : $stopCode")
            _state.value =
                RoutesForStopState(routesForStop = routesForStops[stopCode] ?: emptyList())
        } else {
            getRoutesForStopsUseCase(stopCode).onEach { result ->
                when (result) {
                    is Resource.Success -> {
                        val routes = result.data ?: emptyList()
                        Log.d(TAG, "Got ${routes.size} routesForStop stopcode: $stopCode")

                        // Cache the fetched routes in the HashMap.
                        routesForStops[stopCode] = routes
                        _state.value =
                            RoutesForStopState(routesForStop = result.data ?: emptyList())
                    }

                    is Resource.Error -> {
                        Log.d(TAG, "Error on $stopCode : ${result.message}")
                        _state.value =
                            RoutesForStopState(error = result.message ?: "Unexpected error")
                    }

                    is Resource.Loading -> {
                        _state.value = RoutesForStopState(isLoading = true)
                    }
                }
            }.launchIn(viewModelScope)
        }
    }
//
//    fun getStopArrival(stopCode: String) {
//        getStopArrivalUseCase(stopCode).onEach { result ->
//            when (result) {
//                is Resource.Success -> {
//                    Log.d(TAG, "Got " + result.data?.size + " closestStopsArrival")
//                    val hash = hashMapOf(stopCode to (result.data?: emptyList<Arrival>()))
//                    _state.value =
//                        RoutesForStopState(arrivalsForStop = hash)
//                }
//
//                is Resource.Error -> {
//                    Log.e(
//                        TAG,
//                        "getStopArrivalUseCase" + "stop code: " + stopCode + result.message
//                    )
//                    _state.value =
//                        RoutesForStopState(error = result.message ?: "Unexpected error")
//                }
//
//                is Resource.Loading -> {
//                    _state.value = RoutesForStopState(isLoading = true)
//                }
//            }
//        }.launchIn(viewModelScope)
//    }

}
