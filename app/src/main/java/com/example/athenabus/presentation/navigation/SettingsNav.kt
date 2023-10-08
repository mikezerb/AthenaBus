package com.example.athenabus.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.athenabus.presentation.settings_screen.SettingsScreen

fun NavGraphBuilder.onSettingsNavGraph() {
    navigation(
        route = Graph.SETTINGS,
        startDestination = Route.SettingsActivityScreen.route
    ) {
        composable(
            route = Route.SettingsActivityScreen.route
        ) {
            SettingsScreen()
        }
    }
}