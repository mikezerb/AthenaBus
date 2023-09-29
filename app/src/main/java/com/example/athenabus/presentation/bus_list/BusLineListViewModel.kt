package com.example.athenabus.presentation.bus_list

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.athenabus.common.Resource
import com.example.athenabus.domain.use_case.bus_lines.getLinesUseCase
import com.example.athenabus.domain.use_case.get_route.GetRouteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject


@HiltViewModel
class BusLineListViewModel @Inject constructor(
    private val getLinesUseCase: getLinesUseCase,
    private val getRouteUseCase: GetRouteUseCase
) : ViewModel() {

    private val _state = mutableStateOf(BusLineListState())
    val state: State<BusLineListState> = _state

    init {
        getBusLines()
    }

    private fun getBusLines() {
        getLinesUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = BusLineListState(bus_lines = result.data ?: emptyList())
                }

                is Resource.Error -> {
                    _state.value = BusLineListState(error = result.message ?: "Unexpected error")
                }

                is Resource.Loading -> {
                    _state.value = BusLineListState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }


}