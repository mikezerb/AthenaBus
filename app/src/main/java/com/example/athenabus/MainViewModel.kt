package com.example.athenabus

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.athenabus.domain.use_case.app_entry.AppEntryUseCases
import com.example.athenabus.presentation.navigation.Route
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    appEntryUseCases: AppEntryUseCases
) : ViewModel() {

    var splashCondition by mutableStateOf(true)
        private set

    var startDestination by mutableStateOf(Route.AppStartNavigationScreen.route)
        private set

    init {
        appEntryUseCases.readAppEntryUseCase().onEach { shouldStartFromHomeScreen ->
            startDestination = if (shouldStartFromHomeScreen) {
                Route.MainActivity.route
            } else {
                Route.AppStartNavigationScreen.route
            }
            delay(300)
            splashCondition = false
        }.launchIn(viewModelScope)
    }
}