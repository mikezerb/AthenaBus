package com.example.athenabus.presentation.favorites_screen.components

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.athenabus.domain.model.Line
import com.example.athenabus.sample.SampleLineProvider

@Composable
fun FavoriteLineItem(
    busLine: Line,
    navController: NavController = rememberNavController(),
) {
    Box {
        Text(text = "FavoriteLineItem")
    }
}

@Preview(name = "FavoriteLineItem")
@Composable
private fun PreviewFavoriteLineItem(@PreviewParameter(SampleLineProvider::class) busLine: Line) {
    FavoriteLineItem(busLine = busLine)
}