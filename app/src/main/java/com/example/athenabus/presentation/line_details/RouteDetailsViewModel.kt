package com.example.athenabus.presentation.line_details

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.athenabus.common.Resource
import com.example.athenabus.domain.use_case.get_route.GetRouteFromLineIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class RouteDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getRouteFromLineIdUseCase: GetRouteFromLineIdUseCase,
) : ViewModel() {

    private val _state = mutableStateOf(RouteDetailsState())
    val state: State<RouteDetailsState> = _state

    init {
//        savedStateHandle.get<String>(Constants.PARAM_LINE_ID)?.let { lineId ->
//            getLineCodes(lineId)
//        }
    }

    fun getLineCodes(lineId: String) {
        getRouteFromLineIdUseCase(lineId).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    Log.d("getLineCodes", "Found: " + result.data?.size.toString() + " with lineID: " + lineId)
                    _state.value = RouteDetailsState(availableRoutes = result.data ?: emptyList())
                }

                is Resource.Error -> {
                    Log.d("getLineCodes", "Error found: " + result.message + " with lineID: " + lineId)
                    _state.value = RouteDetailsState(error = result.message ?: "Unexpected error")
                }

                is Resource.Loading -> {
                    _state.value = RouteDetailsState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }
}
