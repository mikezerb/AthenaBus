package com.example.athenabus.presentation.common

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.athenabus.R
import com.example.athenabus.presentation.navigation.Route

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MaterialTopAppBar(
    title: String,
    isLineDetailsScreen: Boolean = false,
    canNavigateBack: Boolean = false,
    navigateUp: () -> Unit = { },
    navigationDrawerClick: () -> Unit = { },
    onFavoriteClick: () -> Unit = { },
    onMapClick: () -> Unit = { },
    onMoreSettings: () -> Unit = { },
    onSettingsClick: () -> Unit = { },
    navController: NavController = rememberNavController()
    // TODO: Add click for map, favorite or schedule alarm buttons
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val visible = currentRoute == Route.NewBusLineScreen.route
            || currentRoute == Route.FavoriteScreen.route
            || currentRoute == Route.ClosestStopsActivityScreen.route
            || currentRoute == Route.SettingsActivityScreen.route
            || currentRoute == Route.AboutActivityScreen.route

//    if (currentRoute == Route.LineDetailActivityScreen.route) {
//        AnimatedVisibility(
//            visible = currentRoute == Route.LineDetailActivityScreen.route,
//            enter = slideInVertically(initialOffsetY = { -it }),
//            exit = slideOutVertically(targetOffsetY = { -it }),
//        )
//        {
//            LargeTopAppBar(
//                title = {
//                    Column {
//                        Text(text = navBackStackEntry?.arguments?.getString("lineId") ?: "")
//                        Text(
//                            text = navBackStackEntry?.arguments?.getString("lineDesc") ?: "",
//                            maxLines = 1,
//                            style = MaterialTheme.typography.titleMedium
//                        )
//                    }
//                },
//                actions = {
//                    IconButton(onClick = onMapClick) {
//                        Icon(imageVector = Icons.Default.Map, contentDescription = null)
//                    }
//                    IconButton(onClick = onFavoriteClick) {
//                        if (navBackStackEntry?.arguments?.getBoolean("isFav") == true)
//                            Icon(imageVector = Icons.Default.Star, contentDescription = null)
//                        else {
//                            Icon(imageVector = Icons.Default.StarBorder, contentDescription = null)
//                        }
//                    }
//                    IconButton(onClick = onMoreSettings) {
//                        Icon(imageVector = Icons.Default.MoreVert, contentDescription = null)
//                    }
//                },
//                navigationIcon = {
//                    IconButton(onClick = navigateUp) {
//                        Icon(
//                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
//                            contentDescription = "Back"
//                        )
//                    }
//                },
//            )
//        }
//    } else {
    AnimatedVisibility(
        visible = visible,
        enter = slideInVertically(initialOffsetY = { -it }),
        exit = slideOutVertically(targetOffsetY = { -it }),
    ) {
        CenterAlignedTopAppBar(
            title = {
                Text(
                    text = if (currentRoute == Route.AboutActivityScreen.route) stringResource(R.string.about_athenabus) else title,
                    fontFamily = FontFamily(Font(R.font.poppins_semibold))
                )
            },
            navigationIcon = {
                if (currentRoute == Route.SettingsActivityScreen.route || currentRoute == Route.AboutActivityScreen.route) {
                    IconButton(onClick = navigateUp) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                } else {
                    IconButton(onClick = navigationDrawerClick) {
                        Icon(imageVector = Icons.Default.Menu, contentDescription = null)
                    }
                }
            },
            actions = {
                if (
                    currentRoute == Route.NewBusLineScreen.route
                    || currentRoute == Route.FavoriteScreen.route
                    || currentRoute == Route.ClosestStopsActivityScreen.route
                ) {
                    IconButton(onClick = onSettingsClick) {
                        Icon(
                            imageVector = Icons.Outlined.Settings,
                            contentDescription = "Settings"
                        )
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        )
    }
}


@Preview
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun HomeScreenTopAppBarPreview() {
    MaterialTopAppBar(
        title = "AthenaBus",
    )
}

@Preview
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun LineDetailsScreenTopAppBarPreview() {
    MaterialTopAppBar(
        title = "608",
    )

}
