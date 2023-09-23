package com.example.athenabus.presentation.search_screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.athenabus.common.Resource
import com.example.athenabus.domain.repository.BusLineRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchLinesViewModel @Inject constructor(
    private val repository: BusLineRepository
) : ViewModel() {

    var state by mutableStateOf(SearchLinesState())
    private var searchJob: Job? = null
    fun onEvent(event: SearchLinesEvent) {
        when (event) {
            is SearchLinesEvent.Refresh -> {

            }

            is SearchLinesEvent.OnSearchQueryChange -> {
                state = state.copy(searchQuery = event.query)
                searchJob?.cancel()
                searchJob = viewModelScope.launch {
                    delay(500L)
                    getLines()
                }


            }
        }
    }

    private fun getLines(query: String = state.searchQuery.uppercase()) {
        viewModelScope.launch {
            repository
                .getLineFromDB(query)
                .collect() { result ->
                    when (result) {
                        is Resource.Success -> {
                            result.data?.let { busLines ->
                                state = state.copy(
                                    lines = busLines
                                )
                            }
                        }

                        is Resource.Error -> Unit
                        is Resource.Loading -> {
                            state = state.copy(isLoading = true)
                        }
                    }
                }
        }
    }
}