package com.example.athenabus.presentation.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.athenabus.presentation.about_screen.AboutScreen
import com.example.athenabus.presentation.bus_list.NewBusLineListScreen
import com.example.athenabus.presentation.closest_stops.NewClosestStopsScreen
import com.example.athenabus.presentation.favorites_screen.FavoriteScreen
import com.example.athenabus.presentation.line_details.LineDetailsScreen
import com.example.athenabus.presentation.settings_screen.SettingsScreen

@Composable
fun HomeNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Route.NewBusLine.route,
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
        navigation(
            startDestination = Route.NewBusLineScreen.route,
            route = Route.NewBusLine.route
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
                NewClosestStopsScreen(navController = navController)
            }
            composable(route = Route.AboutActivityScreen.route){
                AboutScreen(navController = navController)
            }
            navigation(
                startDestination = Route.LineDetailActivityScreen.route,
                route = Route.LineDetailsActivity.route
            ) {
                composable(
                    route = Route.LineDetailActivityScreen.route,
                    arguments = listOf(
                        navArgument(name = "lineId") { type = NavType.StringType },
                        navArgument(name = "lineCode") { type = NavType.StringType },
                        navArgument(name = "lineDesc") { type = NavType.StringType },
                        navArgument(name = "isFav") { type = NavType.BoolType },
                    )
                ) { navBackStackEntry ->
                    LineDetailsScreen(
                        lineId = navBackStackEntry.arguments?.getString("lineId") ?: "",
                        lineCode = navBackStackEntry.arguments?.getString("lineCode") ?: "",
                        lineDesc = navBackStackEntry.arguments?.getString("lineDesc") ?: "",
                        isFav = navBackStackEntry.arguments?.getBoolean("isFav") ?: false,
                        navController = navController
                    )
                }
            }

        }
    }
}
