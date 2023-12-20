package com.example.athenabus.presentation.favorites

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.athenabus.common.Resource
import com.example.athenabus.domain.use_case.get_stops.GetFavoriteStopsUseCase
import com.example.athenabus.presentation.favorites.tabs.FavoriteStopsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class FavoriteScreenStopsViewModel @Inject constructor(
    private val getFavoriteStopsUseCase: GetFavoriteStopsUseCase,

    ) : ViewModel() {

    private val _state = mutableStateOf(FavoriteStopsState())
    val state: State<FavoriteStopsState> = _state

    init {
        getFavStops()
    }

    fun getFavStops() {
        getFavoriteStopsUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    val favStops = result.data ?: emptyList()
                    Log.d("UseCase", "result: ${result.data?.size}")
                    _state.value = FavoriteStopsState(favoriteStops = favStops)
                }

                is Resource.Error -> {
                    Log.d("UseCase", "Error: ${result.message}")
                    _state.value =
                        FavoriteStopsState(error = result.message ?: "Unexpected error")
                }

                is Resource.Loading -> {
                    _state.value = FavoriteStopsState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }
}