package com.example.athenabus.presentation.line_details

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.athenabus.common.Resource
import com.example.athenabus.domain.use_case.get_route.GetDailySchedulesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class RouteScheduleViewModel @Inject constructor(
    private val getDailySchedulesUseCase: GetDailySchedulesUseCase
) : ViewModel() {
    private val _state = mutableStateOf(RouteScheduleState())
    val state: State<RouteScheduleState> = _state

    fun getDailySchedules(lineCode: String) {
        getDailySchedulesUseCase(lineCode).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = RouteScheduleState(schedules = result.data)
                }

                is Resource.Error -> {
                    _state.value = RouteScheduleState(error = result.message ?: "Unexpected error")
                }

                is Resource.Loading -> {
                    _state.value = RouteScheduleState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }
}