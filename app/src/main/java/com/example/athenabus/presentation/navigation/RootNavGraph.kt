package com.example.athenabus.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun RootNavGraph(navController: NavHostController, startDestination: String) {
    NavHost(
        navController = navController,
        route = Graph.ROOT,
        startDestination = Graph.ONBOARDING
    ) {
        onBoardingNavGraph()
        startNavGraph(navController)
    }
}
