package com.example.athenabus.presentation.navigation

sealed class Route(
    val route: String
) {
    object OnBoardingScreen : Route(route = "onBoardingScreen")
    object MainActivity : Route(route = "mainActivity")
    object HomeScreen : Route(route = "homeScreen")
    object FavoriteActivity : Route(route = "favoriteActivity")
    object FavoriteScreen : Route(route = "favoriteScreen")
    object BusLine : Route(route = "BusLine")
    object BusLineScreen : Route(route = "busLineScreen")
    object NewBusLine : Route(route = "newBusLine")
    object NewBusLineScreen : Route(route = "newBusLineScreen")
    object BusArrivalScreen : Route(route = "busArrivalScreen")
    object AppStartNavigationScreen : Route(route = "appStartNavigation")
    object BusRoutes : Route(route = "busNavigation")
    object BusNavigatorScreen : Route(route = "busNavigatorScreen")
    object BusRoutesScreen : Route(route = "busRouteScreen")
    object RoutesDetail : Route(route = "RoutesDetail")
    object RoutesDetailScreen : Route(route = "RoutesDetailScreen")
    object SearchLine : Route(route = "SearchLine")
    object SearchLineScreen : Route(route = "SearchLineScreen")
    object SettingsActivity : Route(route = "settings")
    object SettingsActivityScreen : Route(route = "settingsActivityScreen")
    object ClosestStopsActivity : Route(route = "closestStops")
    object ClosestStopsActivityScreen : Route(route = "closestStopsActivity")
    object LineDetailsActivity : Route(route = "lineDetailsScreen")
    object LineDetailActivityScreen :
        Route(route = "lineDetailsScreen?lineId={lineId}&lineCode={lineCode}&lineDesc={lineDesc}&isFav={isFav}")

    object AboutActivity : Route(route = "aboutActivity")
    object AboutActivityScreen : Route(route = "aboutActivityScreen")

}
