package com.example.athenabus.presentation.nvgraph

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.athenabus.presentation.bus_list.NewBusLineListScreen
import com.example.athenabus.presentation.closest_stops.ClosestStopsScreen
import com.example.athenabus.presentation.favorites_screen.FavoriteScreen
import com.example.athenabus.presentation.settings_screen.SettingsScreen

fun NavGraphBuilder.homeGraph(navController: NavController) {
    navigation(
        startDestination = Route.NewBusLineScreen.route,
        route = Route.HomeScreen.route
    ) {
        composable(route = Route.NewBusLineScreen.route) {
            NewBusLineListScreen(navController)
        }

        composable(route = Route.FavoriteScreen.route) {
            FavoriteScreen(navController)
        }

        composable(route = Route.SettingsActivityScreen.route) {
            SettingsScreen(navController = navController)
        }

        composable(route = Route.ClosestStopsActivityScreen.route) {
            ClosestStopsScreen(navController = navController)
        }
    }
}
