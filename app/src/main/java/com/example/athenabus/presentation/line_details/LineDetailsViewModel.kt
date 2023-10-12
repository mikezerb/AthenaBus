package com.example.athenabus.presentation.line_details

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.athenabus.common.Resource
import com.example.athenabus.domain.use_case.bus_lines.GetLineFromIDUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class LineDetailsViewModel @Inject constructor(
    private val getLineFromIDUseCase: GetLineFromIDUseCase
) : ViewModel() {

    private val _state = mutableStateOf(LineDetailsState())
    val state: State<LineDetailsState> = _state

    fun getLine(lineId: String) {
        getLineFromIDUseCase(lineId).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    val busLines = result.data ?: emptyList()
                    Log.d(
                        "LineDetailsViewModel",
                        "Found ${busLines.size} lines with lineId: $lineId"
                    )
                    _state.value = LineDetailsState(lines = busLines)
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
