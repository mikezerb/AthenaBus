package com.example.athenabus.presentation.closest_stops

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.athenabus.domain.location.LocationTracker
import com.example.athenabus.domain.model.Line
import com.example.athenabus.presentation.bus_list.BusLineListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LocationViewModel @Inject constructor(
    private val locationTracker: LocationTracker
) : ViewModel() {

    private val _state = mutableStateOf(LocationState())
    val state: State<LocationState> = _state

    fun getCurrentLocation() {
        viewModelScope.launch {
            val currentLocation = locationTracker.getCurrentLocation()
            Log.d("location", "Location: " + currentLocation?.latitude.toString() + " " + currentLocation?.longitude.toString())
            _state.value = LocationState(currentLocation)
        }
    }
}