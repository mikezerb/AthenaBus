package com.example.athenabus.presentation.favorites

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.athenabus.common.Resource
import com.example.athenabus.domain.model.Route
import com.example.athenabus.domain.use_case.bus_lines.GetFavoriteLinesUseCase
import com.example.athenabus.domain.use_case.get_route.GetRoutesForStopsUseCase
import com.example.athenabus.domain.use_case.get_stops.GetFavoriteStopsUseCase
import com.example.athenabus.domain.use_case.get_stops.RemoveFavoriteStopUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class FavoriteScreenViewModel @Inject constructor(
    private val getFavoriteLinesUseCase: GetFavoriteLinesUseCase,
    private val getFavoriteStopsUseCase: GetFavoriteStopsUseCase,
    private val getRoutesForStopsUseCase: GetRoutesForStopsUseCase,
    private val removeFavoriteStopUseCase: RemoveFavoriteStopUseCase
) : ViewModel() {

    private val _state = mutableStateOf(FavoriteLinesState())
    val state: State<FavoriteLinesState> = _state

    private val _stopState = mutableStateOf(FavoriteStopsState())
    val stopState: State<FavoriteStopsState> = _stopState

    private val routesForStop = HashMap<String, List<Route>>()

    init {
        getFavLines()
        getFavStops()
    }

    fun checkFavoriteStops() {
        getFavStops()
    }

    fun checkFavoriteLines() {
        getFavLines()
    }

    private fun getFavLines() {
        getFavoriteLinesUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    val favLines = result.data ?: emptyList()
                    _state.value = FavoriteLinesState(favoriteLines = favLines)
                }

                is Resource.Error -> {
                    _state.value =
                        FavoriteLinesState(error = result.message ?: "Unexpected error")
                }

                is Resource.Loading -> {
                    _state.value = FavoriteLinesState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun getFavStops() {
        getFavoriteStopsUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    val favStops = result.data ?: emptyList()
                    favStops.forEach { stop ->
                        getRouteForStop(stop.StopCode)
                    }
                    _stopState.value =
                        FavoriteStopsState(favoriteStops = favStops, routesForStops = routesForStop)
                }

                is Resource.Error -> {
                    _stopState.value =
                        FavoriteStopsState(error = result.message ?: "Unexpected error")
                }

                is Resource.Loading -> {
                    _stopState.value = FavoriteStopsState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun getRouteForStop(stopCode: String) {
        getRoutesForStopsUseCase(stopCode).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    val favStops = result.data ?: emptyList()
                    routesForStop[stopCode] = favStops
                }

                is Resource.Error -> {
                    _stopState.value =
                        FavoriteStopsState(error = result.message ?: "Unexpected error")
                }

                is Resource.Loading -> {
                    _stopState.value = FavoriteStopsState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    suspend fun removeFavoriteStop(stopCode: String) {
        removeFavoriteStopUseCase(stopCode)
    }
}