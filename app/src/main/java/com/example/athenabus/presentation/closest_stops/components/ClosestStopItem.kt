package com.example.athenabus.presentation.closest_stops.components

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun ClosestStopItem(
    modifier: Modifier = Modifier
) {
    Box(modifier) {
        Text(text = "ClosestStopItem")
    }
}

@Preview(name = "ClosestStopItem")
@Composable
private fun PreviewClosestStopItem() {
    ClosestStopItem()
}