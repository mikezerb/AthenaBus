package com.example.athenabus.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.athenabus.presentation.main_screen.MainScreen

fun NavGraphBuilder.startNavGraph(navController: NavController){
    navigation(
        startDestination = Graph.HOME,
        route = Graph.START
    ){
        composable(route = Graph.HOME) {
            MainScreen()
        }
    }
}
