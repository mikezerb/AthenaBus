package com.example.athenabus.presentation.closest_stops

import android.Manifest
import android.location.Location
import android.util.Log
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideOut
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastDistinctBy
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.athenabus.R
import com.example.athenabus.domain.model.Arrival
import com.example.athenabus.domain.model.Route
import com.example.athenabus.presentation.closest_stops.components.EnableLocation
import com.example.athenabus.presentation.closest_stops.components.ExpandableClosestStopItem
import com.example.athenabus.presentation.settings.AppTheme
import com.example.athenabus.presentation.settings.ThemeViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.MarkerComposable
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import kotlinx.coroutines.launch

private const val TAG = "NewClosestStopsScreen"

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun NewClosestStopsScreen(
    navController: NavController = rememberNavController(),
    viewModel: NewLocationViewModel = hiltViewModel(),
    closestStopsViewModel: ClosestStopsViewModel = hiltViewModel(),
    routesForStopViewModel: RoutesForStopViewModel = hiltViewModel(),
    arrivalsForStopViewModel: ArrivalsForStopViewModel = hiltViewModel(),
    themeViewModel: ThemeViewModel = hiltViewModel(),
    paddingValues: PaddingValues
) {
    val locationPermissions = rememberMultiplePermissionsState(
        permissions = listOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
    )

    var expandedItem by remember {
        mutableIntStateOf(-1)
    }

    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    val locationValue = viewModel.state.value
    val closestStopsValue = closestStopsViewModel.state.value
    val routesForStopValue = routesForStopViewModel.state.value
    val arrivalsForStopValue = arrivalsForStopViewModel.state.value
    val darkThemeState by themeViewModel.themeState.collectAsState()

    val routeStates = remember { mutableStateMapOf<String, List<Route>>() }

    val locationPermissionState = rememberMultiplePermissionsState(
        listOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
        )
    ) { permissions ->
        when {
            permissions.all { it.value } -> viewModel.getCurrentLocation()
            else -> locationPermissions.launchMultiplePermissionRequest()
        }
    }

    LaunchedEffect(key1 = locationPermissionState.allPermissionsGranted) {
        //locationPermissionState.launchMultiplePermissionRequest()
        if (locationPermissionState.allPermissionsGranted) {
            viewModel.getCurrentLocation()
        }
    }


    LaunchedEffect(key1 = locationValue.currentLocation) {
        if (locationValue.currentLocation != null) {
            Log.d("LaunchedEffect", " closestStopsViewModel")
            closestStopsViewModel.getClosestStops(
                x = locationValue.currentLocation?.latitude.toString(),
                y = locationValue.currentLocation?.longitude.toString()
            )
        }
    }
    LaunchedEffect(key1 = closestStopsValue.closestStops.isNotEmpty()) {
        if (closestStopsValue.closestStops.isNotEmpty()) {
            Log.d("LaunchedEffect", " closestStopsValue")
            closestStopsValue.closestStops.forEach { stop ->
                routesForStopViewModel.getRoutesForStop(stop.StopCode)
            }
        }
    }

    LaunchedEffect(key1 = routesForStopValue.routesForStop.isNotEmpty()) {
        if (routesForStopValue.routesForStop.isNotEmpty()) {
            Log.d("LaunchedEffect + routesForStopValue", " routesForStopValue ")
            closestStopsValue.closestStops.forEach { stop ->
                Log.d("closestStopsValue", "Stop: " + stop.StopDescr + " " + stop.StopID)
                arrivalsForStopViewModel.getStopArrival(stop.StopCode)
            }
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

    var showSnackbar by remember { mutableStateOf(false) }
    val defaultAthensCoords = LatLng(37.981, 23.7273)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(defaultAthensCoords, 11f)
    }
    // Set properties using MapProperties which you can use to recompose the map
    val mapProperties = MapProperties(
        // Only enable if user has accepted location permissions.
        isMyLocationEnabled = locationValue.currentLocation != null,
        mapStyleOptions =
        if (darkThemeState.appTheme == AppTheme.FOLLOW_SYSTEM) {
            if (isSystemInDarkTheme()) {
                MapStyleOptions.loadRawResourceStyle(context, R.raw.map_style_dark)
            } else {
                MapStyleOptions.loadRawResourceStyle(context, R.raw.map_style)
            }
        } else if (darkThemeState.appTheme == AppTheme.LIGHT) {
            MapStyleOptions.loadRawResourceStyle(context, R.raw.map_style)
        } else {
            MapStyleOptions.loadRawResourceStyle(context, R.raw.map_style_dark)
        }
    )
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
        contentAlignment = Alignment.TopCenter
    ) {
        AnimatedContent(targetState = locationPermissionState.allPermissionsGranted, label = "")
        { areGranted ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                if (areGranted) {
                    Surface(tonalElevation = 2.dp) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Loc: ${locationValue.currentLocation?.latitude ?: "N/A"} ${locationValue.currentLocation?.longitude ?: ""}",
                                style = MaterialTheme.typography.bodySmall,
                                modifier = Modifier.padding(horizontal = 6.dp)
                            )
                            TextButton(
                                modifier = Modifier
                                    .height(34.dp)
                                    .padding(horizontal = 4.dp),
                                onClick = {
                                    viewModel.getCurrentLocation()
                                    if (locationValue.currentLocation != null) {
                                        closestStopsViewModel.getClosestStops(
                                            x = locationValue.currentLocation?.latitude.toString(),
                                            y = locationValue.currentLocation?.longitude.toString()
                                        )
                                    } else {
                                        //showSnackbar = true
                                    }
                                }
                            ) {
                                Icon(imageVector = Icons.Default.Refresh, contentDescription = null)
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = stringResource(id = R.string.update_location),
                                    style = MaterialTheme.typography.labelSmall
                                )
                            }
                        }
                    }
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(0.8f)
                            .padding(16.dp),
                        shape = RoundedCornerShape(20.dp),
                        shadowElevation = 4.dp
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
                            SideEffect {
                                scope.launch {
                                    locationValue.currentLocation?.let {
                                        cameraPositionState.centerOnLocation(
                                            it
                                        )
                                    }
                                }
                            }
                            if (closestStopsValue.closestStops.isNotEmpty()) {
                                closestStopsValue.closestStops.forEach { stop ->
                                    MarkerComposable(
                                        state = MarkerState(
                                            position = LatLng(
                                                stop.StopLat.toDouble(),
                                                stop.StopLng.toDouble()
                                            )
                                        ),
                                        title = stop.StopDescr,
                                        snippet = stop.StopStreet ?: "",
                                        draggable = false,
                                    ) {
                                        Icon(
                                            imageVector = ImageVector.vectorResource(id = R.drawable.bus_stop_pointer),
                                            contentDescription = null,
                                            tint = MaterialTheme.colorScheme.surfaceTint
                                        )
                                    }
                                }
                            }
                        }
                        if (!isMapLoaded) {
                            AnimatedVisibility(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(0.8f),
                                visible = !isMapLoaded,
                                enter = fadeIn(),
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
                    AnimatedVisibility(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(0.8f),
                        visible = !closestStopsValue.isLoading && closestStopsValue.closestStops.isEmpty(),
                        enter = fadeIn(),
                        exit = fadeOut()
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(0.8f)
                                .background(MaterialTheme.colorScheme.surface),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.SpaceEvenly
                        ) {
                            ElevatedButton(
                                onClick = {
                                    viewModel.getCurrentLocation()
                                    if (locationValue.currentLocation != null) {
                                        locationValue.let { locationState ->
                                            closestStopsViewModel.getClosestStops(
                                                x = locationState.currentLocation?.latitude.toString(),
                                                y = locationState.currentLocation?.longitude.toString()
                                            )
                                        }
                                    } else {
//                                        showSnackbar = true
                                    }
                                }) {
                                Text(
                                    text = stringResource(R.string.search_stops),
                                    style = MaterialTheme.typography.headlineSmall,
                                    color = MaterialTheme.colorScheme.surfaceTint
                                )
                            }
                        }
                        AnimatedVisibility(
                            visible = showSnackbar,
                            enter = slideIn(initialOffset = { IntOffset(0, it.height / 2) }),
                            exit = slideOut(targetOffset = { IntOffset(0, it.height / 2) })
                        ) {
                            Snackbar(
                                modifier = Modifier.padding(4.dp),
                                action = {
                                    TextButton(
                                        onClick = {
                                        }) {
                                        Text(text = "Activate")
                                    }
                                },
                                dismissAction = {
                                    IconButton(onClick = { showSnackbar = false }) {
                                        Icon(
                                            imageVector = Icons.Outlined.Close,
                                            contentDescription = null
                                        )
                                    }
                                }
                            ) {
                                Text(
                                    text = "Turn Device Location On",
                                    color = MaterialTheme.colorScheme.primary
                                )

                            }
                        }
                    }
                    AnimatedVisibility(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1.2f),
                        visible = closestStopsValue.closestStops.isNotEmpty(),
                        enter = fadeIn(),
                        exit = fadeOut()
                    ) {
                        Surface(
                            modifier = Modifier
                                .fillMaxSize(),
                            shadowElevation = 8.dp,
                            tonalElevation = 2.dp
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
                                if (closestStopsValue.isLoading || routesForStopValue.isLoading) {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .weight(0.8f),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        LinearProgressIndicator(
                                            modifier = Modifier
                                                .background(MaterialTheme.colorScheme.background)
                                                .wrapContentSize()
                                                .align(Alignment.Center),
                                            color = MaterialTheme.colorScheme.surfaceTint
                                        )
                                    }
                                }
                                if (closestStopsValue.error.isNotEmpty() || routesForStopValue.error.isNotEmpty()) {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .weight(0.8f),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = closestStopsValue.error,
                                            color = MaterialTheme.colorScheme.error,
                                            style = MaterialTheme.typography.headlineSmall
                                        )
                                    }
                                }
                                LazyColumn(
                                    modifier = Modifier.fillMaxWidth(),
                                ) {
                                    items(
                                        closestStopsValue.closestStops
                                    ) { stop ->
                                        ExpandableClosestStopItem(
                                            stop = stop,
                                            onClick = { }, //  closestStopsViewModel.getStopArrival(stopCode = stop.StopCode)
                                            routes = (routesForStopViewModel.routesForStops[stop.StopCode]
                                                ?: emptyList()).fastDistinctBy { it.RouteCode },
                                            arrivals = fillArrivalDetail(
                                                arrivalsForStopViewModel.arrivalsForStop[stop.StopCode]
                                                    ?: emptyList(),
                                                routesForStopViewModel.routesForStops[stop.StopCode]
                                                    ?: emptyList()
                                            ),
                                            expanded = stop.StopCode.toInt() == expandedItem,
                                            onExpandClick = { id ->
                                                expandedItem = if (expandedItem == id) {
                                                    -1
                                                } else {
                                                    id
                                                }

                                            }
                                        )
                                        HorizontalDivider(modifier = Modifier.padding(horizontal = 4.dp))
                                    }
                                }
                            }
                        }
                    }
//
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

private suspend fun CameraPositionState.centerOnLocation(
    location: Location
) = animate(
    update = CameraUpdateFactory.newLatLngZoom(
        LatLng(location.latitude, location.longitude),
        15f
    ),
)

//private fun fillArrivalDetail(arrivals: List<Arrival>, routes: List<Route>) : List<Arrival>{
//    return arrivals.map {
//        routes.find { route -> it.route_code == route.RouteCode }
//        ?.let { it1 -> it.addRoute(it1) }
//    }
//}

private fun fillArrivalDetail(arrivals: List<Arrival>, routes: List<Route>): List<Arrival> {
    return arrivals.map { arrival ->
        val matchingRoute = routes.find { route -> arrival.route_code == route.RouteCode }
        matchingRoute?.let { route ->
            arrival.copy(LineID = route.LineID)
        } ?: arrival
    }
}
