package com.example.athenabus.presentation.line_details

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.athenabus.common.Resource
import com.example.athenabus.domain.use_case.bus_lines.GetLinesFromLineIDUseCase
import com.example.athenabus.domain.use_case.get_route.GetDailySchedulesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class RouteScheduleViewModel @Inject constructor(
    private val getDailySchedulesUseCase: GetDailySchedulesUseCase,
    private val getLinesFromLineIDUseCase: GetLinesFromLineIDUseCase,
) : ViewModel() {
    private val _state = mutableStateOf(RouteScheduleState())
    val state: State<RouteScheduleState> = _state

    fun getDailySchedules(lineCode: String) {
        getDailySchedulesUseCase(lineCode).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    Log.d(
                        "Schedules",
                        "Found come: ${result.data?.come?.size}, go: ${result.data?.go?.size}"
                    )
                    _state.value = RouteScheduleState(schedules = result.data)
                }

                is Resource.Error -> {
                    Log.e("Schedules", "Found error: ${result.message ?: "Unexpected error"}")

                    _state.value = RouteScheduleState(error = result.message ?: "Unexpected error")
                }

                is Resource.Loading -> {
                    _state.value = RouteScheduleState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun getAvailableLines(lineId: String) {
        getLinesFromLineIDUseCase(lineId).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    val busLines = result.data ?: emptyList()
                    Log.d("getAvailableLines", "For lineId: $lineId found ${busLines.size}")
                    _state.value = RouteScheduleState(availableLines = busLines)
                }

                is Resource.Error -> {
                    Log.d("getAvailableLines", "Error ${result.message ?: "Unexpected error"}")
                    _state.value = RouteScheduleState(error = result.message ?: "Unexpected error")
                }

                is Resource.Loading -> {
                    _state.value = RouteScheduleState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }


}