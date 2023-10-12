package com.example.athenabus.presentation.line_details

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
fun LineDetailsScreen(
    modifier: Modifier = Modifier,
    navController: NavController = rememberNavController(),
    lineId: String = "",
    lineCode: String = "",
    lineDesc: String = "",
    isFav: Boolean = false,
    viewModel: LineDetailsViewModel = hiltViewModel()
) {
    Log.d(
        "LineDetailsScreen",
        "LineID: $lineId, LineCode: $lineCode, lineDesc: $lineDesc isFav = $isFav"
    )
    val state = viewModel.state.value

    LaunchedEffect(key1 = Unit) {
        viewModel.getLine(lineId)
    }
    AnimatedVisibility(
        visible = state.lines.isNotEmpty(),
        enter = slideInVertically(),
        exit = slideOutVertically()
    ) {
        Surface {
            Column(
                modifier
                    .fillMaxSize()
                    .padding(4.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "LineDetailsScreen")
                Text(text = lineId)
            }
        }
        AnimatedVisibility(
            visible = state.isLoading,
            modifier = Modifier.fillMaxSize(),
        ) {
            CircularProgressIndicator(
                modifier = Modifier
                    .size(54.dp),
                strokeWidth = 6.dp
            )
        }
    }
}

@Preview(name = "LineDetailsScreen")
@Composable
private fun PreviewLineDetailsScreen() {
    LineDetailsScreen()
}
