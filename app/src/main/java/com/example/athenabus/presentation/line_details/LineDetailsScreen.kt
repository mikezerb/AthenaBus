package com.example.athenabus.presentation.line_details

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun LineDetailsScreen(
    modifier: Modifier = Modifier
) {
    Box(modifier) {
        Text(text = "LineDetailsScreen")
    }
}

@Preview(name = "LineDetailsScreen")
@Composable
private fun PreviewLineDetailsScreen() {
    LineDetailsScreen()
}