package com.example.athenabus.presentation.common

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Route
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Route
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.athenabus.R
import com.example.athenabus.presentation.navigation.Route

sealed class NavBarItem(
    @StringRes val title: Int,
    val selectedIcon: ImageVector,
    val unSelectedIcon: ImageVector,
    val hasAlert: Boolean,
    val badgeCount: Int? = 0,
    val route: String
) {
    data object Routes : NavBarItem(
        title = R.string.routes_nav_bar,
        route = Route.NewBusLineScreen.route,
        selectedIcon = Icons.Filled.Route,
        unSelectedIcon = Icons.Outlined.Route,
        hasAlert = false
    )

    data object Favorites : NavBarItem(
        title = R.string.favorites_nav_bar,
        selectedIcon = Icons.Filled.Favorite,
        unSelectedIcon = Icons.Outlined.FavoriteBorder,
        hasAlert = false,
        route = Route.FavoriteScreen.route
    )

    data object Settings : NavBarItem(
        title = R.string.settings_nav_bar,
        selectedIcon = Icons.Filled.Settings,
        unSelectedIcon = Icons.Outlined.Settings,
        hasAlert = false,
        route = Route.SettingsActivityScreen.route
    )

    data object ClosestStops : NavBarItem(
        title = R.string.closest_stops_nav_bar,
        selectedIcon = Icons.Filled.LocationOn,
        unSelectedIcon = Icons.Outlined.LocationOn,
        hasAlert = false,
        route = Route.ClosestStopsActivityScreen.route
    )
}