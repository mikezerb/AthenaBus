package com.example.athenabus.presentation.favorites.tabs

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun FavoriteStopsScreen(
    modifier: Modifier = Modifier
) {
    Column(modifier) {
        Text(text = "FavoriteStopsScreen")
    }
}

@Preview(name = "FavoriteStopsScreen")
@Composable
private fun PreviewFavoriteStopsScreen() {
    FavoriteStopsScreen()
}