package com.example.athenabus.presentation.main_screen.components

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.NewReleases
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.NewReleases
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.athenabus.R
import com.example.athenabus.common.Constants
import com.example.athenabus.presentation.navigation.Route

sealed class NavDrawerItem(
    @StringRes val title: Int,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val badgeCount: Int? = null,
    val destination: String? = null,
    val link: String? = null,
) {
    data object HomeDrawer : NavDrawerItem(
        title = R.string.all_drawer_title,
        selectedIcon = Icons.Filled.Home,
        unselectedIcon = Icons.Outlined.Home,
        destination = Route.NewBusLineScreen.route
    )

    data object FavoritesDrawer : NavDrawerItem(
        title = R.string.favorites_drawer_title,
        selectedIcon = Icons.Filled.Favorite,
        unselectedIcon = Icons.Outlined.FavoriteBorder,
        destination = Route.FavoriteScreen.route
    )

    data object SettingsDrawer : NavDrawerItem(
        title = R.string.settings_drawer_title,
        selectedIcon = Icons.Filled.Settings,
        unselectedIcon = Icons.Outlined.Settings,
        destination = Route.SettingsActivityScreen.route
    )

    data object NewsDrawer : NavDrawerItem(
        title = R.string.oasa_news_drawer_title,
        selectedIcon = Icons.Filled.NewReleases,
        unselectedIcon = Icons.Outlined.NewReleases,
        link = Constants.OASA_NEWS_LINK
    )
}
