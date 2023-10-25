package com.example.athenabus.presentation.line_details

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.athenabus.common.Resource
import com.example.athenabus.domain.use_case.bus_lines.GetLineFromIDUseCase
import com.example.athenabus.domain.use_case.bus_lines.GetLinesFromLineIDUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class LineDetailsViewModel @Inject constructor(
    private val getLineFromIDUseCase: GetLineFromIDUseCase,
    private val getLinesFromLineIDUseCase: GetLinesFromLineIDUseCase,
) : ViewModel() {

    private val _state = mutableStateOf(LineDetailsState())
    val state: State<LineDetailsState> = _state

    fun getLine(lineId: String) {
        getLineFromIDUseCase(lineId).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    val busLines = result.data
                    _state.value = LineDetailsState(line = busLines)
                }

                is Resource.Error -> {
                    _state.value = LineDetailsState(error = result.message ?: "Unexpected error")
                }

                is Resource.Loading -> {
                    _state.value = LineDetailsState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun getAvailableLines(lineId: String){
        getLinesFromLineIDUseCase(lineId).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    val busLines = result.data ?: emptyList()
                    _state.value = LineDetailsState(availableLines = busLines)
                }

                is Resource.Error -> {
                    _state.value = LineDetailsState(error = result.message ?: "Unexpected error")
                }

                is Resource.Loading -> {
                    _state.value = LineDetailsState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

}
