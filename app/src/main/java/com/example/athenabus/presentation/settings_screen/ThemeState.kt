package com.example.athenabus.presentation.settings_screen

data class ThemeState(
    val isAmoledMode: Boolean,
    val isDynamicMode: Boolean,
    val appTheme: Int = 0
)
