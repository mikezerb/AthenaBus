package com.example.athenabus.presentation.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.TransformOrigin
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.athenabus.presentation.about.AboutScreen
import com.example.athenabus.presentation.bus_list.BusLineListScreen
import com.example.athenabus.presentation.closest_stops.NewClosestStopsScreen
import com.example.athenabus.presentation.favorites.FavoriteScreen
import com.example.athenabus.presentation.line_details.LineDetailsScreen
import com.example.athenabus.presentation.settings.SettingsScreen

@Composable
fun MainNavGraph(navController: NavHostController, paddingValues: PaddingValues) {
    NavHost(
        navController = navController,
        route = Graph.MAIN,
        startDestination = Route.NewBusLineScreen.route,
        enterTransition = {
            slideIntoContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Companion.Right,
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
            BusLineListScreen(navController = navController, paddingValues = paddingValues)
        }

        composable(route = Route.FavoriteScreen.route) {
            FavoriteScreen(navController = navController, paddingValues = paddingValues)
        }

        composable(route = Route.SettingsActivityScreen.route) {
            SettingsScreen(navController = navController, paddingValues = paddingValues)
        }

        composable(route = Route.ClosestStopsActivityScreen.route) {
            NewClosestStopsScreen(navController = navController, paddingValues = paddingValues)
        }

        composable(route = Route.AboutActivityScreen.route) {
            AboutScreen(paddingValues = paddingValues)
        }

        composable(
            route = Route.LineDetailActivityScreen.route,
            arguments = listOf(
                navArgument(name = "lineId") { type = NavType.StringType },
                navArgument(name = "lineCode") { type = NavType.StringType },
                navArgument(name = "lineDesc") { type = NavType.StringType },
                navArgument(name = "isFav") { type = NavType.BoolType },
            ),
            enterTransition = {
                slideInVertically(
                    initialOffsetY = { -40 }
                ) + expandVertically(
                    expandFrom = Alignment.Top
                ) + scaleIn(
                    transformOrigin = TransformOrigin(0.5f, 0f)
                ) + fadeIn(initialAlpha = 0.3f)
            },
            exitTransition = {
                slideOutVertically() + shrinkVertically() + fadeOut() + scaleOut(targetScale = 1.2f)
            }
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