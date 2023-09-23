package com.example.athenabus.presentation.nvgraph

sealed class Route(
    val route: String
) {
    object OnBoardingScreen : Route(route = "onBoardingScreen")
    object HomeScreen : Route(route = "homeScreen")
    object BusLine : Route(route = "BusLine")
    object BusLineScreen : Route(route = "busLineScreen")
    object BusArrivalScreen : Route(route = "busArrivalScreen")
    object AppStartNavigationScreen : Route(route = "appStartNavigation")
    object BusRoutes : Route(route = "busNavigation")
    object BusNavigatorScreen : Route(route = "busNavigatorScreen")
    object BusRoutesScreen : Route(route = "busRouteScreen")
    object RoutesDetail : Route(route = "RoutesDetail")
    object RoutesDetailScreen : Route(route = "RoutesDetailScreen")
    object SearchLine : Route(route = "SearchLine")
    object SearchLineScreen : Route(route = "SearchLineScreen")

}
