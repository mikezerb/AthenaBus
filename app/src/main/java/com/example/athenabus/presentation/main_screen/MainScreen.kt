package com.example.athenabus.presentation.main_screen

import android.content.Intent
import android.net.Uri
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.athenabus.R
import com.example.athenabus.presentation.common.BackPressAction
import com.example.athenabus.presentation.common.MaterialBottomNavBar
import com.example.athenabus.presentation.common.MaterialTopAppBar
import com.example.athenabus.presentation.main_screen.components.DrawerHeader
import com.example.athenabus.presentation.main_screen.components.NavDrawerItem
import com.example.athenabus.presentation.navigation.MainNavGraph
import com.example.athenabus.presentation.navigation.Route
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    navController: NavHostController = rememberNavController()
) {

    BackPressAction()

    /**
     * bottom bar variables for nested scroll
     */
    val bottomBarHeight = 56.dp
    val bottomBarOffsetHeightPx = remember { mutableStateOf(0f) }

    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    val snackbarHostState = remember { SnackbarHostState() }
    var showBottomBar by rememberSaveable { mutableStateOf(true) }
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
//    val connectivityObserver = NetworkConnectivityObserver(context)
//    val networkStatus by connectivityObserver.observe()
//        .collectAsState(initial = ConnectionStatus.Initial)
    showBottomBar = when (navBackStackEntry?.destination?.route) {
        Route.SettingsActivityScreen.route -> false // on this screen bottom bar should be hidden
        Route.LineDetailActivityScreen.route -> false // on this screen bottom bar should be hidden
        Route.AboutActivityScreen.route -> false // on this screen bottom bar should be hidden
        Route.StopActivityScreen.route -> false // on this screen bottom bar should be hidden
        Route.LinesOnMapActivityScreen.route -> false // on this screen bottom bar should be hidden
        else -> true // in all other cases show bottom bar
    }

    val navDrawerItems =
        listOf(
            NavDrawerItem.HomeDrawer,
            NavDrawerItem.FavoritesDrawer,
            NavDrawerItem.SettingsDrawer,
            NavDrawerItem.NewsDrawer
        )
    var selectedDrawerItemIndex by rememberSaveable {
        mutableIntStateOf(0)
    }
    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        val drawerState = rememberDrawerState(
            initialValue = DrawerValue.Closed
        )
        ModalNavigationDrawer(
            gesturesEnabled = drawerState.isOpen,
            drawerContent = {
                ModalDrawerSheet(
                    drawerTonalElevation = 4.dp,
                ) {
                    Spacer(modifier = Modifier.height(16.dp))
                    DrawerHeader(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        onClick = {
                            scope.launch {
                                drawerState.close()
                            }
                            navController.navigate(route = Route.AboutActivityScreen.route) {
                                popUpTo(Route.HomeScreen.route)
                                launchSingleTop = true
                            }
                        }
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    navDrawerItems.forEachIndexed { index, navItem ->
                        NavigationDrawerItem(
                            modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding),
                            label = { Text(text = stringResource(id = navItem.title)) },
                            selected = index == selectedDrawerItemIndex,
                            onClick = {
                                selectedDrawerItemIndex = index
                                scope.launch {
                                    drawerState.close()
                                }
                                if (navItem.destination != null) {
                                    navController.navigate(route = navItem.destination) {
                                        popUpTo(Route.HomeScreen.route)
                                        launchSingleTop = true
                                    }
                                }
                                if (navItem.link != null) {
                                    val intent = Intent(
                                        Intent.ACTION_VIEW,
                                        Uri.parse(navItem.link)
                                    )
                                    context.startActivity(intent)
                                }
                            },
                            icon = {
                                Icon(
                                    imageVector =
                                    if (index == selectedDrawerItemIndex)
                                        navItem.selectedIcon
                                    else navItem.unselectedIcon,
                                    contentDescription = null
                                )
                            },

                            badge = {
                                navItem.badgeCount?.let {
                                    Text(text = navItem.badgeCount.toString())
                                }
                            }
                        )
                    }
                }
            },
            drawerState = drawerState
        ) {
            Scaffold(
                modifier = Modifier.fillMaxSize(),
                topBar = {
                    MaterialTopAppBar(
                        title = stringResource(id = R.string.app_name),
                        canNavigateBack = navController.previousBackStackEntry != null,
                        navigateUp = { navController.popBackStack() },
                        navigationDrawerClick = { scope.launch { drawerState.open() } },
                        onSettingsClick = {
                            navController.navigate(Route.SettingsActivityScreen.route) {
                                popUpTo(Route.HomeScreen.route)
                                launchSingleTop = true
                            }
                        },
                        navController = navController
                    )
                },
                snackbarHost = {
                    SnackbarHost(hostState = snackbarHostState)
                },
                bottomBar = {
                    AnimatedVisibility(
                        visible = showBottomBar,
                        enter = slideInVertically(initialOffsetY = { it }),
                        exit = slideOutVertically(targetOffsetY = { it }),
                    ) {
                        MaterialBottomNavBar(navController = navController)
                    }
                },
            ) {
                MainNavGraph(navController = navController, paddingValues = it)
            }
        }
    }
}

@Preview(name = "MainScreen")
@Composable
private fun PreviewMainScreen() {
    MainScreen(navController = rememberNavController())
}
