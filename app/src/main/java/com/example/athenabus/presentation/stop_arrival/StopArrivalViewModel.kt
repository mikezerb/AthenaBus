package com.example.athenabus.presentation.stop_arrival

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.athenabus.common.Resource
import com.example.athenabus.domain.use_case.get_stops.GetStopDetailsFromCodeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class StopArrivalViewModel @Inject constructor(
    private val getStopDetailsFromCodeUseCase: GetStopDetailsFromCodeUseCase
) : ViewModel() {

    private val _state = mutableStateOf(StopArrivalState())
    val state: State<StopArrivalState> = _state

    fun getStopDetails(stopCode: String) {
        getStopDetailsFromCodeUseCase(stopCode).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    val busLines = result.data

                    _state.value = StopArrivalState(busStop = busLines)
                }

                is Resource.Error -> {
                    _state.value = StopArrivalState(error = result.message ?: "Unexpected error")
                }

                is Resource.Loading -> {
                    _state.value = StopArrivalState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

}