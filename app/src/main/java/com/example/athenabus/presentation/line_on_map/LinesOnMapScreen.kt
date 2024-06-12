package com.example.athenabus.presentation.line_on_map

import DropdownMenuSelection
import android.util.Log
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.DirectionsBus
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.athenabus.R
import com.example.athenabus.domain.model.Stop
import com.example.athenabus.presentation.settings.AppTheme
import com.example.athenabus.presentation.settings.ThemeViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.MarkerComposable
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LinesOnMapScreen(
    navController: NavController = rememberNavController(),
    viewModel: LinesOnMapViewModel = hiltViewModel(),
    themeViewModel: ThemeViewModel = hiltViewModel(),
    lineId: String
) {
    val state = viewModel.state.value
    val stopsState = viewModel.stopsState.value
    val routesState = viewModel.routesState.value

    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    val darkThemeState by themeViewModel.themeState.collectAsState()

    var isMapLoaded by remember { mutableStateOf(false) }

    val uiSettings by remember {
        mutableStateOf(
            MapUiSettings(
                compassEnabled = true,
                myLocationButtonEnabled = true,
            )
        )
    }

    val athens = LatLng(37.981, 23.7273)

    val startCoords = LatLng(athens.latitude, athens.longitude)

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(startCoords, 10f)
    }
    // Set properties using MapProperties which you can use to recompose the map
    val mapProperties = MapProperties(
        // Only enable if user has accepted location permissions.
        isMyLocationEnabled = false,
        mapStyleOptions = if (darkThemeState.appTheme == AppTheme.FOLLOW_SYSTEM) {
            if (isSystemInDarkTheme()) {
                MapStyleOptions.loadRawResourceStyle(context, R.raw.map_style_dark)
            } else {
                MapStyleOptions.loadRawResourceStyle(context, R.raw.map_style)
            }
        } else if (darkThemeState.appTheme == AppTheme.LIGHT) {
            MapStyleOptions.loadRawResourceStyle(context, R.raw.map_style)
        } else {
            MapStyleOptions.loadRawResourceStyle(context, R.raw.map_style_dark)
        },
        // southwest, northeast borders of greece (most likely inaccurate)
        latLngBoundsForCameraTarget = LatLngBounds(LatLng(34.800, 19.376), LatLng(41.748, 29.65)),
        minZoomPreference = 8f,
    )

    var expanded by remember { mutableStateOf(false) }

    var selectedRoute by remember {
//        mutableStateOf(routesState.routes.first().RouteDescr)
        mutableStateOf("")
    }

    var selectedRouteCode by remember {
//        mutableStateOf(routesState.routes.first().RouteCode)
        mutableStateOf("")
    }

    LaunchedEffect(key1 = stopsState.routeStops) {
        scope.launch {
            if (stopsState.routeStops.isNotEmpty()) {
                val bounds = stopsState.routeStops.calculateStopsLatLngBounds()
                cameraPositionState.setPosition(bounds.center, 12f)
                cameraPositionState.setMapBoundsBasedOnStops(bounds = bounds)
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(R.string.bus_locations)) },
                actions = {
                    IconButton(onClick = {
                        scope.launch {
                            viewModel.getRoutesFromLineId(lineId)
                        }
                    }) {
                        Icon(imageVector = Icons.Default.Refresh, contentDescription = null)
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }

            )
        },
    )
    { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            DropdownMenuSelection(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                itemList = routesState.routes.map { it.RouteDescr },
                initialText = stringResource(id = R.string.choose_direction),
                onDismiss = { expanded = false },
                onClick = { route, i ->
                    selectedRoute = route
                    selectedRouteCode = routesState.routes[i].RouteCode
                    expanded = !expanded
                    viewModel.updateData(selectedRouteCode)
                },
                selectedOption = selectedRoute,
                expanded = expanded,
                onExpanded = { expanded = it },
            )
            if (state.isLoading || routesState.isLoading || stopsState.isLoading) {
                CircularProgressIndicator()
            } else if (state.error.isNotEmpty() || routesState.error.isNotEmpty() || stopsState.error.isNotEmpty()) {
                Text(text = state.error + " " + routesState.error)
            } else if (state.busLocations.isNotEmpty() || routesState.routes.isNotEmpty() || stopsState.routeStops.isNotEmpty()) {
                Box(
                    Modifier
                        .fillMaxSize()
                        .padding(8.dp)
                        .clip(RoundedCornerShape(20.dp)),
                    contentAlignment = Alignment.TopCenter,
                ) {
                    GoogleMap(
                        modifier = Modifier.fillMaxSize(),
                        cameraPositionState = cameraPositionState,
                        properties = mapProperties,
                        uiSettings = uiSettings,
                        onMapLoaded = {
                            isMapLoaded = true
                        },
                    ) {
                        if (state.busLocations.isNotEmpty()) {
                            state.busLocations.forEach { busLocation ->
                                MarkerComposable(
                                    state = MarkerState(
                                        position = LatLng(
                                            busLocation.CS_LAT.toDouble(),
                                            busLocation.CS_LNG.toDouble()
                                        )
                                    ),
                                    title = busLocation.VEH_NO,
                                ) {
                                    Icon(
                                        modifier = Modifier.scale(1.2f),
                                        imageVector = Icons.Default.DirectionsBus,
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.tertiary
                                    )
                                }
                            }
                        }

                        if (stopsState.routeStops.isNotEmpty()) {
                            stopsState.routeStops.forEach { stop ->
                                Log.d(" state.routeStops", "stop: ${stop.StopLat}, ${stop.StopLng}")
                                MarkerComposable(
                                    state = MarkerState(
                                        position = LatLng(
                                            stop.StopLat.toDouble(),
                                            stop.StopLng.toDouble()
                                        )
                                    ),
                                    title = stop.StopDescr,
                                    snippet = stop.StopCode,
                                ) {
                                    Icon(
                                        modifier = Modifier.scale(0.8f),
                                        imageVector = ImageVector.vectorResource(id = R.drawable.bus_stop_pointer),
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.surfaceTint
                                    )
                                }
                            }
                        }

//                        SideEffect {
//                            scope.launch {
//                                if (stopsState.routeStops.isNotEmpty()) {
//                                    val bounds = stopsState.routeStops.calculateStopsLatLngBounds()
////                                    cameraPositionState.setPosition(bounds.center)
//                                    cameraPositionState.setMapBoundsBasedOnStops(bounds = bounds)
//                                }
//                            }
//                        }

                    }

                }

            }
        }
    }
}


/**
 * Sets and animates the map bounds based on the provided [LatLngBounds] with optional padding.
 */
private suspend fun CameraPositionState.setMapBoundsBasedOnStops(
    bounds: LatLngBounds, padding: Int = 8
) = animate(
    update = CameraUpdateFactory.newLatLngBounds(bounds, padding)
)

private suspend fun CameraPositionState.setPosition(
    latLng: LatLng, zoom: Float = 8f
) = animate(
    update = CameraUpdateFactory.newCameraPosition(CameraPosition.fromLatLngZoom(latLng, zoom))
)

/**
 * Calculates and returns a [LatLngBounds] encompassing the coordinates of a list of stops.
 */
private fun List<Stop>.calculateStopsLatLngBounds(): LatLngBounds {
    val builder = LatLngBounds.builder()
    forEach { stop ->
        val lat = stop.StopLat.toDouble()
        val lng = stop.StopLng.toDouble()
        builder.include(LatLng(lat, lng))
    }
    return builder.build()
}

@Preview
@Composable
private fun PreviewLinesOnMapScreen() {
    LinesOnMapScreen(lineId = "")
}