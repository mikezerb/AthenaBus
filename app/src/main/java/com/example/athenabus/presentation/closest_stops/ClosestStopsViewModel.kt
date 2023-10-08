package com.example.athenabus.presentation.closest_stops

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.athenabus.common.Resource
import com.example.athenabus.domain.use_case.bus_lines.GetStopsFromLocation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ClosestStopsViewModel @Inject constructor(
    private val getStopsFromLocation: GetStopsFromLocation
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
                    _state.value = ClosestStopsState(error = result.message ?: "Unexpected error")
                }
                is Resource.Loading -> {
                    _state.value = ClosestStopsState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

}