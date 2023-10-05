package com.example.athenabus.presentation.nvgraph

sealed class Screen(val route: String) {
    object SettingsScreen : Screen("settings_screen")
}