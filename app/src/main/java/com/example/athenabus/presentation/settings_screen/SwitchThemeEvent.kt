package com.example.athenabus.presentation.settings_screen

// SwitchThemeEvent
sealed class SwitchThemeEvent {
    data object ToggleAppTheme : SwitchThemeEvent()
}