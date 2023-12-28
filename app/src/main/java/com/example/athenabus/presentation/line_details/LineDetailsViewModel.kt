package com.example.athenabus.presentation.line_details

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.athenabus.common.Constants
import com.example.athenabus.common.Resource
import com.example.athenabus.domain.model.Line
import com.example.athenabus.domain.use_case.bus_lines.AddFavoriteLineUseCase
import com.example.athenabus.domain.use_case.bus_lines.GetLineFromIDUseCase
import com.example.athenabus.domain.use_case.bus_lines.GetLinesFromLineIDUseCase
import com.example.athenabus.domain.use_case.bus_lines.IsFavoriteLineUseCase
import com.example.athenabus.domain.use_case.bus_lines.RemoveFavoriteLineUseCase
import com.example.athenabus.domain.use_case.get_route.GetDailySchedulesUseCase
import com.example.athenabus.domain.use_case.get_route.GetRouteFromLineIdUseCase
import com.example.athenabus.domain.use_case.get_stops.GetStopsFromRouteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class LineDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getLineFromIDUseCase: GetLineFromIDUseCase,
    private val getLinesFromLineIDUseCase: GetLinesFromLineIDUseCase,
    private val addFavoriteLineUseCase: AddFavoriteLineUseCase,
    private val removeFavoriteLineUseCase: RemoveFavoriteLineUseCase,
    private val isFavoriteLineUseCase: IsFavoriteLineUseCase,
    private val getRouteFromLineIdUseCase: GetRouteFromLineIdUseCase,
    private val getDailySchedulesUseCase: GetDailySchedulesUseCase,
    private val getStopsFromRouteUseCase: GetStopsFromRouteUseCase
) : ViewModel() {

    private val _state = mutableStateOf(LineDetailsState())
    val state: State<LineDetailsState> = _state

    private val _routeState = mutableStateOf(RouteDetailsState())
    val routeState: State<RouteDetailsState> = _routeState

    private val _scheduleState = mutableStateOf(RouteScheduleState())
    val scheduleState: State<RouteScheduleState> = _scheduleState


    private val _stopState = mutableStateOf(RouteStopsState())
    val stopState: State<RouteStopsState> = _stopState

    init {
        savedStateHandle.get<String>(Constants.PARAM_LINE_ID)?.let { lineId ->
            getLine(lineId)
            getLineCodes(lineId)
            getAvailableLines(lineId)
        }
    }

    private fun getLine(lineId: String) {
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

    private fun getLineCodes(lineId: String) {
        getRouteFromLineIdUseCase(lineId).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    Log.d(
                        "getLineCodes",
                        "Found: " + result.data?.size.toString() + " with lineID: " + lineId
                    )
                    _routeState.value =
                        RouteDetailsState(availableRoutes = result.data ?: emptyList())
                }

                is Resource.Error -> {
                    Log.d(
                        "getLineCodes",
                        "Error found: " + result.message + " with lineID: " + lineId
                    )
                    _routeState.value =
                        RouteDetailsState(error = result.message ?: "Unexpected error")
                }

                is Resource.Loading -> {
                    _routeState.value = RouteDetailsState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }


    suspend fun isFavoriteLine(lineId: String): Boolean {
        return isFavoriteLineUseCase(lineId)
    }

    suspend fun addFavoriteLine(line: Line) {
        addFavoriteLineUseCase(line)
    }

    suspend fun removeFavoriteLine(line: String) {
        removeFavoriteLineUseCase(line)
    }


    fun getDailySchedules(lineCode: String) {
        getDailySchedulesUseCase(lineCode).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    Log.d(
                        "Schedules",
                        "Found come: ${result.data?.come?.size}, go: ${result.data?.go?.size}"
                    )
                    _scheduleState.value = RouteScheduleState(schedules = result.data)
                }

                is Resource.Error -> {
                    Log.e("Schedules", "Found error: ${result.message ?: "Unexpected error"}")

                    _scheduleState.value =
                        RouteScheduleState(error = result.message ?: "Unexpected error")
                }

                is Resource.Loading -> {
                    _scheduleState.value = RouteScheduleState(isLoading = true)
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
                    _scheduleState.value = RouteScheduleState(availableLines = busLines)
                }

                is Resource.Error -> {
                    Log.d("getAvailableLines", "Error ${result.message ?: "Unexpected error"}")
                    _scheduleState.value =
                        RouteScheduleState(error = result.message ?: "Unexpected error")
                }

                is Resource.Loading -> {
                    _scheduleState.value = RouteScheduleState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun getStops(routeCode: String) {
        getStopsFromRouteUseCase(routeCode).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    val stops = result.data ?: emptyList()
                    _stopState.value = RouteStopsState(stops = stops)
                }

                is Resource.Error -> {
                    _stopState.value = RouteStopsState(error = result.message ?: "Unexpected error")
                }

                is Resource.Loading -> {
                    _stopState.value = RouteStopsState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }
}
