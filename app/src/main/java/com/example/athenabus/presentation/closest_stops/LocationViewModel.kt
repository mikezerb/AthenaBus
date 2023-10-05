package com.example.athenabus.presentation.closest_stops

import android.location.Location
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.athenabus.domain.location.LocationTracker
import com.example.athenabus.presentation.bus_list.BusLineListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LocationViewModel @Inject constructor(
    private val locationTracker: LocationTracker
) : ViewModel() {
    var currentLocation by mutableStateOf<Location?>(null)

    val currentLoc: MutableLiveData<Location> by lazy {
        MutableLiveData<Location>()
    }



    private val _state = mutableStateOf<Location?>(null)
    val state: State<Location?> = _state
    fun getCurrentLocation() {
        viewModelScope.launch {
            currentLocation = locationTracker.getCurrentLocation()
            currentLoc.postValue(currentLocation)
        }
    }
}