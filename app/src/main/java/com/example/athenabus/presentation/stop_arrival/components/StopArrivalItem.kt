package com.example.athenabus.presentation.stop_arrival.components

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun StopArrivalItem(
    modifier: Modifier = Modifier
) {
    Box(modifier) {
        Text(text = "StopArrivalItem")
    }
}

@Preview(name = "StopArrivalItem")
@Composable
private fun PreviewStopArrivalItem() {
    StopArrivalItem()
}