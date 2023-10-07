package com.example.athenabus.presentation.stop_arrival

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun StopArrivalScreen(
    modifier: Modifier = Modifier
) {
    Box(modifier) {
        Text(text = "StopArrivalScreen")
    }
}

@Preview(name = "StopArrivalScreen")
@Composable
private fun PreviewStopArrivalScreen() {
    StopArrivalScreen()
}