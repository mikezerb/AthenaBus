package com.example.athenabus.presentation.common

import android.content.res.Configuration
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.athenabus.ui.theme.AthenaBusTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MaterialBottomNavBar(
    navController: NavController
) {
    NavigationBar(
        modifier = Modifier.padding(0.dp)
    ) {
        val items = listOf(
            NavBarItem.Routes,
            NavBarItem.Favorites,
            NavBarItem.Settings
        )

        var selectedItemIndex by rememberSaveable {
            mutableIntStateOf(0)
        }

        val nabBackStackEntry by navController.currentBackStackEntryAsState()

        val currentDestination = nabBackStackEntry?.destination

        val bottomBarDestination = items.any { it.route == currentDestination?.route }

        val scale: Float = 1.2f

        val animatedScale: Float by animateFloatAsState(
            targetValue = scale,
            animationSpec = TweenSpec(
                durationMillis = 2000,
                easing = FastOutSlowInEasing
            ), label = ""
        )

        items.forEachIndexed { index, navBarItem ->
            NavigationBarItem(
                modifier = Modifier.then(
                    Modifier.scale(
                        if (selectedItemIndex == index) animatedScale else 1f
                    )
                ),
                selected = selectedItemIndex == index,
                onClick = {
                    selectedItemIndex = index
                    //navController.navigate(navBarItem.title)
                    navController.navigate(navBarItem.route) {
                        navController.graph.startDestinationRoute?.let { route ->
                            popUpTo(route) {
                                saveState = true
                            }
                        }
                        // Avoid multiple copies of the same destination when
                        // reselecting the same item
                        launchSingleTop = true
                        // Restore state when reselecting a previously selected item
                        restoreState = true
                    }
                },
                icon = {
                    BadgedBox(badge = {
                        if (navBarItem.badgeCount != null) {
                            Badge {
                                Text(text = navBarItem.badgeCount.toString())
                            }
                        } else if (navBarItem.hasAlert) {
                            Badge()
                        }
                    }) {
                        Icon(
                            imageVector = if (index == selectedItemIndex) {
                                navBarItem.selectedIcon
                            } else {
                                navBarItem.unSelectedIcon
                            }, contentDescription = null
                        )
                    }
                },
                label = { Text(text = navBarItem.title) }
            )

        }


    }

}

@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
private fun PreviewMaterialBottomNavBar() {
    AthenaBusTheme {
        MaterialBottomNavBar(navController = rememberNavController())
    }
}