package com.example.athenabus.presentation.favorites

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.athenabus.common.Resource
import com.example.athenabus.domain.model.Route
import com.example.athenabus.domain.use_case.get_route.GetRoutesForStopsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteStopsRouteViewModel @Inject constructor(
    private val getRoutesForStopsUseCase: GetRoutesForStopsUseCase,
) : ViewModel() {

    private val _state = mutableStateOf(FavoriteStopsRouteState())
    val state: State<FavoriteStopsRouteState> = _state

    fun getRoutesForStops(stopCodes : List<String>){
        stopCodes.forEach {  code ->
            getRoutesForStopsUseCase(code).onEach { result ->


            }

        }

    }
    fun getRouteForStop(stopCode: String){
        val routesForStop = HashMap<String, List<Route>>()
        getRoutesForStopsUseCase(stopCode).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    val favStops = result.data ?: emptyList()
                    Log.d("Routes", "${favStops.size}")
                    routesForStop[stopCode] = favStops
                    Log.d("routesForStop", "${routesForStop.size}")
                    _state.value = FavoriteStopsRouteState(routes = favStops, routesForStops = routesForStop)
                }
                is Resource.Error -> {
                    Log.d("UseCase", "Error: ${result.message}")
                    _state.value =
                        FavoriteStopsRouteState(error = result.message ?: "Unexpected error")
                }
                is Resource.Loading -> {
                    _state.value = FavoriteStopsRouteState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)

    }

}
