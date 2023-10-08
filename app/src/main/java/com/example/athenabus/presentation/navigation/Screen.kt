package com.example.athenabus.presentation.navigation

sealed class Screen(val route: String) {
    object SettingsScreen : Screen("settings_screen")
}