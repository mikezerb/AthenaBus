package com.example.athenabus.presentation.bus_list

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.athenabus.common.Resource
import com.example.athenabus.domain.model.Line
import com.example.athenabus.domain.use_case.bus_lines.GetLinesFromSearchUseCase
import com.example.athenabus.domain.use_case.bus_lines.ToggleFavoriteLineUseCase
import com.example.athenabus.domain.use_case.bus_lines.GetLinesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject


@HiltViewModel
class BusLineListViewModel @Inject constructor(
    private val getLinesUseCase: GetLinesUseCase,
    private val getLinesFromSearchUseCase: GetLinesFromSearchUseCase,
    private val toggleFavoriteLineUseCase: ToggleFavoriteLineUseCase
) : ViewModel() {

    private val _state = mutableStateOf(BusLineListState())
    val state: State<BusLineListState> = _state

    private val allBusLines = mutableListOf<Line>() // Store all bus lines

    init {
        getBusLines()
    }

    private fun getBusLines() {
        getLinesUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    val busLines = result.data ?: emptyList()
                    allBusLines.clear()
                    allBusLines.addAll(busLines)
                    _state.value = BusLineListState(bus_lines = busLines)
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

    // Function to filter bus lines based on search query
    fun searchBusLines(query: String) {
        val filteredBusLines = if (query.isEmpty()) {
            allBusLines // Show all bus lines when the query is empty
        } else {
            getLinesFromSearchUseCase(allBusLines, query)
        }
        _state.value = BusLineListState(bus_lines = filteredBusLines)
    }

    suspend fun toggleFavoriteLine(query: String) {
        toggleFavoriteLineUseCase(query)
    }
}