package com.example.athenabus.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.athenabus.presentation.bus_list.BusLineListScreen
import com.example.athenabus.presentation.theme.AthenaBusTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContent {
            AthenaBusTheme {
                Surface (color = MaterialTheme.colorScheme.background)
                {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = Screen.BusLineListScreen.route ){
                        composable(
                            route = Screen.BusLineListScreen.route
                        ){
                            BusLineListScreen(navController)
                        }
                    }
                }
            }
        }
    }
}