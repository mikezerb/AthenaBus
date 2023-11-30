package com.example.athenabus.presentation.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.athenabus.presentation.onboarding.OnBoardingScreen
import com.example.athenabus.presentation.onboarding.OnBoardingViewModel

fun NavGraphBuilder.onBoardingNavGraph() {
    navigation(
        route = Graph.ONBOARDING,
        startDestination = Route.OnBoardingScreen.route
    ) {
        composable(
            route = Route.OnBoardingScreen.route
        ) {
            val viewModel: OnBoardingViewModel = hiltViewModel()
            OnBoardingScreen(event = viewModel::onEvent)
        }
    }
}