package com.example.athenabus.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import com.example.athenabus.MainViewModel
import com.example.athenabus.presentation.nvgraph.NavGraph
import com.example.athenabus.presentation.theme.AthenaBusTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    val viewModel by viewModels<MainViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        installSplashScreen().apply {
            setKeepOnScreenCondition {
                viewModel.splashCondition
            }
        }
        setContent {
            AthenaBusTheme {
                Surface(color = MaterialTheme.colorScheme.background)
                {
                    val startingDestination = viewModel.startDestination
                    NavGraph(startDestination = startingDestination)
                }
            }
        }
    }
}