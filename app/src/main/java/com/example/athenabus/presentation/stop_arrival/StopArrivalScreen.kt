package com.example.athenabus.presentation.stop_arrival

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.athenabus.presentation.closest_stops.ArrivalsForStopViewModel
import com.google.maps.android.compose.MapUiSettings

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StopArrivalScreen(
    navController: NavController = rememberNavController(),
    viewModel: ArrivalsForStopViewModel = hiltViewModel(),
    stopArrivalViewModel: StopArrivalViewModel = hiltViewModel(),
    stopCode: String,
    stopDesc: String,
) {

    val state = viewModel.state.value
    val stopState = stopArrivalViewModel.state.value
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    LaunchedEffect(key1 = true, key2 = stopCode) {
        viewModel.getStopArrival(stopCode)
    }

    LaunchedEffect(key1 = true, key2 = stopCode) {
        stopArrivalViewModel.getStopDetails(stopCode)
    }


    var isMapLoaded by remember { mutableStateOf(false) }

    val uiSettings by remember {
        mutableStateOf(
            MapUiSettings(
                compassEnabled = true,
                myLocationButtonEnabled = true,
            )
        )
    }


    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stopDesc)
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },

                )
        }
    ) {
        Surface(Modifier.padding(it)) {
            if (stopState.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .size(54.dp),
                    strokeWidth = 6.dp
                )
            } else if (stopState.busStop != null) {
                Column {
                    Text(text = stopState.busStop.StopLat + " " + stopState.busStop.StopLng)
                }
            } else if (stopState.error.isNotEmpty()) {
                Column {
                    Text(
                        text = stopState.error,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.headlineMedium
                    )
                }
            }

        }

    }


}

@Preview(name = "StopArrivalScreen")
@Composable
private fun PreviewStopArrivalScreen() {
    StopArrivalScreen(stopCode = "60991", stopDesc = "Test")
}