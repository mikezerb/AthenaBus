package com.example.athenabus.presentation.nvgraph

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.athenabus.presentation.bus_list.NewBusLineListScreen
import com.example.athenabus.presentation.closest_stops.ClosestStopsScreen
import com.example.athenabus.presentation.favorites_screen.FavoriteScreen
import com.example.athenabus.presentation.settings_screen.SettingsScreen

@Composable
fun HomeNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Route.NewBusLineScreen.route,
        enterTransition = {
            slideIntoContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Companion.Left,
                animationSpec = tween(500)
            )
        },
        exitTransition = {
            slideOutOfContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Companion.Left,
                animationSpec = tween(500)
            )
        }
    ) {
        composable(route = Route.NewBusLineScreen.route) {
            NewBusLineListScreen(navController)
        }

        composable(route = Route.FavoriteScreen.route) {
            FavoriteScreen(navController)
        }

        navigation(
            route = Route.SettingsActivity.route,
            startDestination = Route.SettingsActivityScreen.route
        ){
            composable(route = Route.SettingsActivityScreen.route) {
                SettingsScreen(navController = navController)
            }
        }
        composable(route = Route.ClosestStopsActivityScreen.route) {
            ClosestStopsScreen(navController = navController)
        }
    }
}