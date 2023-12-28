package com.example.athenabus.presentation.favorites

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.athenabus.common.Resource
import com.example.athenabus.domain.use_case.bus_lines.GetFavoriteLinesUseCase
import com.example.athenabus.domain.use_case.get_stops.GetFavoriteStopsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class FavoriteScreenViewModel @Inject constructor(
    private val getFavoriteLinesUseCase: GetFavoriteLinesUseCase,
    private val getFavoriteStopsUseCase: GetFavoriteStopsUseCase,
    ) : ViewModel() {

    private val _state = mutableStateOf(FavoriteLinesState())
    val state: State<FavoriteLinesState> = _state

    private val _stopState = mutableStateOf(FavoriteStopsState())
    val stopState: State<FavoriteStopsState> = _stopState

    init {
        getFavLines()
        getFavStops()
    }

    private fun getFavLines() {
        getFavoriteLinesUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    val favLines = result.data ?: emptyList()
                    Log.d("UseCaseLines", "result: ${result.data?.size}")
                    _state.value = FavoriteLinesState(favoriteLines = favLines)
                }

                is Resource.Error -> {
                    Log.d("UseCaseLines", "Error: ${result.message}")
                    _state.value =
                        FavoriteLinesState(error = result.message ?: "Unexpected error")
                }

                is Resource.Loading -> {
                    _state.value = FavoriteLinesState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }
    fun getFavStops() {
        getFavoriteStopsUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    val favStops = result.data ?: emptyList()
                    Log.d("UseCaseStops", "result: ${result.data?.size}")
                    _stopState.value = FavoriteStopsState(favoriteStops = favStops)
                }

                is Resource.Error -> {
                    Log.d("UseCaseStops", "Error: ${result.message}")
                    _stopState.value =
                        FavoriteStopsState(error = result.message ?: "Unexpected error")
                }

                is Resource.Loading -> {
                    _stopState.value = FavoriteStopsState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }
}