package com.example.athenabus.presentation.favorites

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.athenabus.common.Resource
import com.example.athenabus.domain.use_case.bus_lines.GetFavoriteLinesUseCase
import com.example.athenabus.presentation.favorites.tabs.FavoriteLinesStopsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class FavoriteScreenViewModel @Inject constructor(
    private val getFavoriteLinesUseCase: GetFavoriteLinesUseCase
) : ViewModel() {

    private val _state = mutableStateOf(FavoriteLinesStopsState())
    val state: State<FavoriteLinesStopsState> = _state

    init {
        getFavLines()
    }

    fun getFavLines() {
        getFavoriteLinesUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    val favLines = result.data ?: emptyList()
                    Log.d("UseCase", "result: ${result.data?.size}")
                    _state.value = FavoriteLinesStopsState(favoriteLines = favLines)
                }

                is Resource.Error -> {
                    Log.d("UseCase", "Error: ${result.message}")
                    _state.value =
                        FavoriteLinesStopsState(error = result.message ?: "Unexpected error")
                }

                is Resource.Loading -> {
                    _state.value = FavoriteLinesStopsState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }
}