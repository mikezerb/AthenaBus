package com.example.athenabus.presentation.common

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Route
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Route
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.athenabus.presentation.nvgraph.Route

sealed class NavBarItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unSelectedIcon: ImageVector,
    val hasAlert: Boolean,
    val badgeCount: Int? = null,
    val route: String
) {
    data object Routes : NavBarItem(
        title = "Routes",
        route = Route.NewBusLineScreen.route,
        selectedIcon = Icons.Filled.Route,
        unSelectedIcon = Icons.Outlined.Route,
        hasAlert = false
    )

    data object Favorites : NavBarItem(
        title = "Favorites",
        selectedIcon = Icons.Filled.Favorite,
        unSelectedIcon = Icons.Outlined.FavoriteBorder,
        hasAlert = false,
        route = Route.FavoriteScreen.route
    )

    data object Settings : NavBarItem(
        title = "Settings",
        selectedIcon = Icons.Filled.Settings,
        unSelectedIcon = Icons.Outlined.Settings,
        hasAlert = false,
        route = Route.SettingsActivityScreen.route
    )
}