package com.example.athenabus.presentation.closest_stops

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
fun ClosestStopsScreen(
    navController: NavController = rememberNavController(),
    modifier: Modifier = Modifier
) {
    Box(modifier) {
        Text(text = "ClosestStopsScreen")
    }
}

@Preview(name = "ClosestStopsScreen")
@Composable
private fun PreviewClosestStopsScreen() {
    ClosestStopsScreen()
}