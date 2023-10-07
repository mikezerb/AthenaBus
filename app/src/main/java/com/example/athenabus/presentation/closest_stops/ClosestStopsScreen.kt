package com.example.athenabus.presentation.closest_stops

import android.location.Location
import android.util.Log
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.athenabus.R
import com.example.athenabus.presentation.closest_stops.components.ClosestStopItem
import com.example.athenabus.presentation.closest_stops.components.EnableLocation
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.rememberCameraPositionState
import kotlinx.coroutines.launch

private const val TAG = "ClosestStopsScreen"

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun ClosestStopsScreen(
    navController: NavController = rememberNavController(),
    viewModel: LocationViewModel = hiltViewModel(),
    closestStopsViewModel: ClosestStopsViewModel = hiltViewModel()
) {
    val athens = LatLng(37.981, 23.7273)

    val locationPermissions = rememberMultiplePermissionsState(
        permissions = listOf(
            android.Manifest.permission.ACCESS_COARSE_LOCATION,
            android.Manifest.permission.ACCESS_FINE_LOCATION
        )
    )

    val value = viewModel.state.value

    val stops_value = closestStopsViewModel.state.value

    SideEffect {
        if (locationPermissions.allPermissionsGranted) {
            viewModel.getCurrentLocation()
            //   closestStopsViewModel.getClosestStops(x = value.currentLocation?.latitude.toString() ?: "0.0", value.currentLocation?.longitude.toString() ?: "0.0")
        }
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

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(athens, 11f)
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {
        AnimatedContent(
            targetState = locationPermissions.allPermissionsGranted, label = ""
        ) { areGranted ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                if (areGranted) {
                    Surface(tonalElevation = 2.dp) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Loc: ${value.currentLocation?.latitude ?: 0.0}, ${value.currentLocation?.longitude ?: 0.0}",
                                style = MaterialTheme.typography.bodyMedium
                            )
                            TextButton(
                                onClick = {
                                    viewModel.getCurrentLocation()
                                    closestStopsViewModel.getClosestStops(
                                        x = value.currentLocation?.latitude.toString() ?: "0.0",
                                        value.currentLocation?.longitude.toString() ?: "0.0"
                                    )
                                }
                            ) {
                                Icon(imageVector = Icons.Default.Refresh, contentDescription = null)
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "Update Location",
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                        }
                    }
                    // Set properties using MapProperties which you can use to recompose the map
                    val mapProperties = MapProperties(
                        // Only enable if user has accepted location permissions.
                        isMyLocationEnabled = value.currentLocation != null,
                    )

                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        tonalElevation = 8.dp
                    ) {
                        GoogleMap(
                            modifier = Modifier.fillMaxSize(),
                            cameraPositionState = cameraPositionState,
                            properties = mapProperties,
                            uiSettings = uiSettings,
                            onMapLoaded = {
                                isMapLoaded = true
                            },
                            onPOIClick = {
                                Log.d(TAG, "POI clicked: ${it.name}")
                            }
                        ) {
                            val scope = rememberCoroutineScope()
                            SideEffect {
                                scope.launch {
                                    value.currentLocation?.let {
                                        cameraPositionState.centerOnLocation(
                                            it
                                        )
                                    }
                                }
                            }
                        }
                        if (!isMapLoaded) {
                            AnimatedVisibility(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(0.5f),
                                visible = !isMapLoaded,
                                enter = EnterTransition.None,
                                exit = fadeOut()
                            ) {
                                CircularProgressIndicator(
                                    modifier = Modifier
                                        .background(MaterialTheme.colorScheme.background)
                                        .wrapContentSize()
                                )
                            }
                        }
                    }
                    if (stops_value.isLoading) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(0.8f),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(
                                modifier = Modifier
                                    .background(MaterialTheme.colorScheme.background)
                                    .wrapContentSize()
                                    .align(Alignment.Center),
                                color = MaterialTheme.colorScheme.surfaceTint
                            )
                        }
                    }
                    if (stops_value.error.isNotEmpty()) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(0.8f),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = stops_value.error,
                                color = MaterialTheme.colorScheme.error,
                                style = MaterialTheme.typography.headlineSmall
                            )
                        }
                    } else if (stops_value.closestStops.isNotEmpty()) {
                        Surface(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1.4f),
                            shadowElevation = 8.dp,
                        ) {
                            Column(
                                modifier = Modifier.fillMaxSize(),
                                verticalArrangement = Arrangement.Top,
                                horizontalAlignment = Alignment.Start
                            ) {
                                Text(
                                    text = "Available stops",
                                    modifier = Modifier.padding(8.dp),
                                    style = MaterialTheme.typography.titleSmall,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                                LazyColumn(
                                    modifier = Modifier.fillMaxWidth(),
                                ) {
                                    items(
                                        stops_value.closestStops
                                    ) { stop ->
                                        ClosestStopItem(
                                            stop = stop,
                                            onClick = { },
                                        )
                                    }
                                }
                            }
                        }
                    } else if (!stops_value.isLoading && isMapLoaded) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(0.6f)
                                .background(MaterialTheme.colorScheme.surface),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.SpaceEvenly
                        ) {
                            ElevatedButton(
                                onClick = {
                                    value.let { locationState ->
                                        closestStopsViewModel.getClosestStops(
                                            x = locationState.currentLocation?.latitude.toString()
                                                ?: "0.0",
                                            locationState.currentLocation?.longitude.toString()
                                                ?: "0.0"
                                        )
                                    }
                                }) {
                                Text(
                                    text = stringResource(R.string.search_stops),
                                    style = MaterialTheme.typography.headlineSmall,
                                    color = MaterialTheme.colorScheme.surfaceTint
                                )

                            }
                        }
                    }
                } else {
                    EnableLocation(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        onClick = { locationPermissions.launchMultiplePermissionRequest() }
                    )
                }
            }
        }
    }
}

/**
 * If you want to center on a specific location.
 */
private suspend fun CameraPositionState.centerOnLocation(
    location: Location
) = animate(
    update = CameraUpdateFactory.newLatLngZoom(
        LatLng(location.latitude, location.longitude),
        15f
    ),
)

@Preview(name = "ClosestStopsScreen")
@Composable
private fun PreviewClosestStopsScreen() {
    ClosestStopsScreen()
}