package com.example.athenabus.presentation.route_list

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.athenabus.common.Constants.PARAM_LINE_ID
import com.example.athenabus.common.Resource
import com.example.athenabus.domain.use_case.bus_lines.GetBusCodesUseCase
import com.example.athenabus.domain.use_case.get_route.GetRouteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class RouteListViewModel @Inject constructor(
    private val getRouteUseCase: GetRouteUseCase,
    private val getBusCodesUseCase: GetBusCodesUseCase,
    savedStateHandle: SavedStateHandle

) : ViewModel() {

    private val _state = mutableStateOf(RouteListState())
    val state: State<RouteListState> = _state

    init {
        savedStateHandle.get<String>(PARAM_LINE_ID)?.let { lineId ->
            getLineCodes(lineId)
        }
    }

    fun getRoutes(lineCode: String) {
        getRouteUseCase(lineCode).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = RouteListState(busRoutes = result.data ?: emptyList())
                }

                is Resource.Error -> {
                    _state.value = RouteListState(error = result.message ?: "Unexpected error")
                }

                is Resource.Loading -> {
                    _state.value = RouteListState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun getLineCodes(lineId: String) {
        getBusCodesUseCase(lineId).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = RouteListState(bus_codes = result.data ?: emptyList())
                    val lineCodes = result.data ?: emptyList()
                    fetchRoutesForLineCodes(lineCodes)
                }

                is Resource.Error -> {
                    _state.value = RouteListState(error = result.message ?: "Unexpected error")

                }

                is Resource.Loading -> {
                    _state.value = RouteListState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun fetchRoutesForLineCodes(lineCodes: List<String>) {
        lineCodes.forEach { lineCode ->
            getRouteUseCase(lineCode).onEach { result ->
                when (result) {
                    is Resource.Success -> {
                        // Append the routes for this lineCode to the existing list
                        val existingRoutes = _state.value.busRoutes
                        val newRoutes = result.data ?: emptyList()
                        _state.value = RouteListState(busRoutes = existingRoutes + newRoutes)
                    }

                    is Resource.Error -> {
                        _state.value = RouteListState(error = result.message ?: "Unexpected error")
                    }

                    is Resource.Loading -> {
                        // You can update the loading state if needed
                    }
                }
            }.launchIn(viewModelScope)
        }
    }
}