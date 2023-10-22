package com.example.athenabus.presentation.settings

data class ThemeState(
    val isAmoledMode: Boolean,
    val isDynamicMode: Boolean,
    val appTheme: Int = 0
)
