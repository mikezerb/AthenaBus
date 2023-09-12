package com.example.athenabus.presentation.nvgraph

sealed class Route(
    val route: String
){
    object OnBoardingScreen: Route(route = "onBoardingScreen")
    object HomeScreen: Route(route = "homeScreen")
    object BusLine: Route(route = "BusLine")
    object BusLineScreen: Route(route = "busLineScreen")
    object BusArrivalScreen: Route(route = "busArrivalScreen")
    object AppStartNavigationScreen: Route(route = "appStartNavigation")
    object BusNavigation: Route(route = "busNavigation")
    object BusNavigatorScreen: Route(route = "busNavigatorScreen")
}
