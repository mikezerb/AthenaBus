package com.example.athenabus.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost

@Composable
fun RootNavigationGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        route = Graph.ROOT,
        startDestination = Graph.ONBOARDING
    ) {
        onBoardingNavGraph()
        startNavGraph(navController)
    }
}
