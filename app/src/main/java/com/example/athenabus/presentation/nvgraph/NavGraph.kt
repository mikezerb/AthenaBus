package com.example.athenabus.presentation.nvgraph

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.example.athenabus.presentation.bus_list.BusLineListScreen
import com.example.athenabus.presentation.onboarding.OnBoardingScreen
import com.example.athenabus.presentation.onboarding.OnBoardingViewModel
import com.example.athenabus.presentation.route_details.RouteDetailsScreen
import com.example.athenabus.presentation.route_list.RouteListScreen
import com.example.athenabus.presentation.search_screen.SearchScreen

@Composable
fun NavGraph(
    startDestination: String
) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = startDestination) {
        navigation(
            route = Route.AppStartNavigationScreen.route,
            startDestination = Route.OnBoardingScreen.route
        ) {
            composable(
                route = Route.OnBoardingScreen.route
            ) {
                val viewModel: OnBoardingViewModel = hiltViewModel()
                OnBoardingScreen(
                    event = viewModel::onEvent
                )
            }
        }

        navigation(
            route = Route.BusLine.route,
            startDestination = Route.BusLineScreen.route
        ) {
            composable(route = Route.BusLineScreen.route) {
                BusLineListScreen(navController)
            }
        }

        navigation(
            route = Route.BusRoutes.route,
            startDestination = Route.BusLineScreen.route
        ) {
            composable(route = Route.BusRoutesScreen.route + "/{lineCode}") {
                RouteListScreen(navController = navController)
            }
        }

        navigation(
            route = Route.RoutesDetail.route,
            startDestination = Route.RoutesDetailScreen.route
        ) {
            composable(route = Route.RoutesDetailScreen.route) {
                RouteDetailsScreen(navController = navController)
            }
        }

        navigation(
            route = Route.SearchLine.route,
            startDestination = Route.SearchLineScreen.route
        ) {
            composable(route = Route.SearchLineScreen.route) {
                SearchScreen(navController = navController)
            }
        }


    }

}