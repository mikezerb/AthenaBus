package com.example.athenabus.presentation.stop_arrival

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.athenabus.common.Constants
import com.example.athenabus.common.Resource
import com.example.athenabus.domain.model.Stop
import com.example.athenabus.domain.use_case.bus_lines.GetStopArrivalUseCase
import com.example.athenabus.domain.use_case.get_route.GetRoutesForStopsUseCase
import com.example.athenabus.domain.use_case.get_stops.AddFavoriteStopUseCase
import com.example.athenabus.domain.use_case.get_stops.GetStopDetailsFromCodeUseCase
import com.example.athenabus.domain.use_case.get_stops.IsFavoriteStopUseCase
import com.example.athenabus.domain.use_case.get_stops.RemoveFavoriteStopUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class StopArrivalViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getStopDetailsFromCodeUseCase: GetStopDetailsFromCodeUseCase,
    private val getStopArrivalUseCase: GetStopArrivalUseCase,
    private val getRoutesForStopsUseCase: GetRoutesForStopsUseCase,
    private val addFavoriteStopUseCase: AddFavoriteStopUseCase,
    private val removeFavoriteStopUseCase: RemoveFavoriteStopUseCase,
    private val isFavoriteStopUseCase: IsFavoriteStopUseCase
) : ViewModel() {

    private val _state = mutableStateOf(StopArrivalState())
    val state: State<StopArrivalState> = _state

    private val _stopState = mutableStateOf(StopState())
    val stopState: State<StopState> = _stopState

    init {
        savedStateHandle.get<String>(Constants.PARAM_STOP_CODE)?.let { stopCode ->
            getStopArrivals(stopCode)
            getRoutesForStop(stopCode)
        }
    }

    private fun getStopDetails(stopCode: String) {
        getStopDetailsFromCodeUseCase(stopCode).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    val busLines = result.data
                    _stopState.value = StopState(busStop = busLines)
                }

                is Resource.Error -> {
                    _stopState.value = StopState(error = result.message ?: "Unexpected error")
                }

                is Resource.Loading -> {
                    _stopState.value = StopState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun getStopArrivals(stopCode: String) {
        getStopArrivalUseCase(stopCode).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    val arrivals = result.data ?: emptyList()
                    Log.d("getStopArrivals", "arrivals: ${arrivals.size}")
                    _state.value = StopArrivalState(stopArrivals = arrivals)
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

    private fun getRoutesForStop(stopCode: String) {
        getRoutesForStopsUseCase(stopCode).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    val routes = result.data
                    Log.d("getStopArrivals", "routes: ${routes?.size}")
                    _stopState.value = StopState(routeStops = routes ?: emptyList())
                }

                is Resource.Error -> {
                    Log.d("getStopArrivals", "error: ${result.message ?: "Unexpected error"}")
                    _stopState.value = StopState(error = result.message ?: "Unexpected error")
                }

                is Resource.Loading -> {
                    _stopState.value = StopState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    suspend fun isFavoriteStop(stopCode: String): Boolean {
        return isFavoriteStopUseCase(stopCode)
    }

    suspend fun addFavoriteStop(stop: Stop) {
        addFavoriteStopUseCase(stop)
    }

    suspend fun removeFavoriteStop(stopCode: String) {
        removeFavoriteStopUseCase(stopCode)
    }

}