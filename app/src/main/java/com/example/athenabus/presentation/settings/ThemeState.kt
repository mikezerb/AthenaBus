package com.example.athenabus.presentation.settings

data class ThemeState(
    val isAmoledMode: Boolean,
    val isDynamicMode: Boolean,
    val appTheme: AppTheme = AppTheme.FOLLOW_SYSTEM
)

enum class AppTheme {
    FOLLOW_SYSTEM,
    LIGHT,
    DARK,
}
