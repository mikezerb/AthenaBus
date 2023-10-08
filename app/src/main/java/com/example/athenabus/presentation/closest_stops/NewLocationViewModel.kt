package com.example.athenabus.presentation.closest_stops

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.athenabus.common.Resource
import com.example.athenabus.domain.use_case.get_location.GetCurrentLocationUseCase
import com.example.athenabus.presentation.bus_list.BusLineListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewLocationViewModel @Inject constructor(
    private val getCurrentLocationUseCase: GetCurrentLocationUseCase
) : ViewModel() {

    private val _state = mutableStateOf(LocationState())
    val state: State<LocationState> = _state

    init {
        getCurrentLocation()
    }

    fun getCurrentLocation(){
        getCurrentLocationUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = LocationState(currentLocation = result.data)
                }
                is Resource.Error -> {
                    _state.value = LocationState(error = result.message ?: "Unexpected error")
                }
                is Resource.Loading -> {
                    _state.value = LocationState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }
}