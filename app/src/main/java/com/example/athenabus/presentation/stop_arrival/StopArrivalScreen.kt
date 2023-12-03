package com.example.athenabus.presentation.stop_arrival

import android.widget.Toast
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.StarBorder
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.athenabus.R
import com.example.athenabus.presentation.closest_stops.ArrivalsForStopViewModel
import com.example.athenabus.presentation.settings.ThemeViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
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

@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@Composable
fun StopArrivalScreen(
    navController: NavController = rememberNavController(),
    viewModel: ArrivalsForStopViewModel = hiltViewModel(),
    stopArrivalViewModel: StopArrivalViewModel = hiltViewModel(),
    themeViewModel: ThemeViewModel = hiltViewModel(),
    stopCode: String,
    stopDesc: String,
) {

    val state = viewModel.state.value
    val stopState = stopArrivalViewModel.state.value
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val darkThemeState by themeViewModel.themeState.collectAsState()

    val sheetState = rememberStandardBottomSheetState(
        initialValue = SheetValue.Expanded
    )
    val scaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = sheetState
    )

    var openBottomSheet = remember {
        mutableStateOf(false)
    }
    var showSnackbar by remember { mutableStateOf(false) }

    var checked by remember {
        mutableStateOf(
            false
        ) // initially checked, default to false if null
    }
//
//    LaunchedEffect(key1 = true, key2 = stopCode) {
//        viewModel.getStopArrival(stopCode)
//    }

    LaunchedEffect(key1 = true, key2 = stopCode) {
        stopArrivalViewModel.getStopDetails(stopCode)
    }

    LaunchedEffect(key1 = true) {
        viewModel.getStopArrival(stopCode)
    }

    LaunchedEffect(key1 = true, key2 = stopCode) {
        stopArrivalViewModel.getRoutesForStop(stopCode)
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

    val defaultAthensCoords = LatLng(37.981, 23.7273)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(defaultAthensCoords, 15f)
    }
    // Set properties using MapProperties which you can use to recompose the map
    val mapProperties = MapProperties(
        // Only enable if user has accepted location permissions.
        isMyLocationEnabled = false,
        mapStyleOptions = if (darkThemeState.appTheme == 2 || isSystemInDarkTheme()) {
            MapStyleOptions.loadRawResourceStyle(context, R.raw.map_style_dark)
        } else {
            MapStyleOptions.loadRawResourceStyle(context, R.raw.map_style)
        }
    )

    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(text = stopDesc)
                        Text(
                            text = stopCode,
                            maxLines = 1,
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.ExtraLight)
                        )
                    }
                },
                actions = {
                    IconToggleButton(
                        checked = checked,
                        onCheckedChange = { _checked ->
                            scope.launch {
                                if (_checked) {
                                    Toast.makeText(
                                        context,
                                        "Added stop to favorites",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    //state.line?.let { viewModel.addFavoriteLine(it) }
                                } else {
                                    Toast.makeText(
                                        context,
                                        "Removed stop from favorites",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    //state.line?.let { viewModel.removeFavoriteLine(lineId) }
                                }
                            }
                            checked = _checked
                        }) {
                        Icon(
                            imageVector = if (checked) Icons.Default.Star else Icons.Outlined.StarBorder,
                            contentDescription = null
                        )
                    }
                    IconButton(onClick = {
                        scope.launch {
                            stopArrivalViewModel.getStopArrivals(stopCode)
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
                },
            )
        },
        sheetContent = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .defaultMinSize(minHeight = 150.dp)
                    .wrapContentSize(),
                contentAlignment = Alignment.TopCenter
            ) {
                Column {
//                    if (stopState.stopArrivals.isEmpty()) {
//                        Text(
//                            text = "No Incoming Buses",
//                            style = MaterialTheme.typography.headlineSmall
//                        )
//                    } else {
                    Text(
                        text = "Incoming Buses",
                        style = MaterialTheme.typography.headlineSmall
                    )
                    if (stopState.stopArrivals.isNotEmpty()) {
                        LazyColumn(modifier = Modifier.fillMaxWidth()) {
                            items(stopState.stopArrivals) { item ->
//                            val lineId =
//                                stopState.routeStops.find { it.RouteCode == item.route_code }?.LineDescr
                                Text(text = item.LineID + " in " + item.btime2)
                            }
                        }
                    } else if (stopState.isLoading) {
                        CircularProgressIndicator()
                    } else if (stopState.error.isNotEmpty()) {
                        Text(text = stopState.error)
                    }

//                    if (stopState.routeStops.isNotEmpty()) {
//                        LazyColumn(modifier = Modifier.fillMaxWidth()) {
//                            items(stopState.routeStops) { route ->
//                                Text(
//                                    text = route.RouteDescr + " " + route.LineID,
//                                    style = MaterialTheme.typography.bodySmall
//                                )
//                            }
//                        }
//                    }
                }
            }
        }
    ) {
        Box(
            Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            if (stopState.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .size(54.dp),
                    strokeWidth = 6.dp
                )
            } else if (stopState.error.isNotEmpty()) {
                Column {
                    Text(
                        text = stopState.error,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.headlineMedium
                    )
                }
            } else if (stopState.busStop != null) {
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
                            cameraPositionState.centerOnLatLng(
                                LatLng(
                                    stopState.busStop.StopLat.toDouble(),
                                    stopState.busStop.StopLng.toDouble()
                                )
                            )
                        }
                    }
                    MarkerComposable(
                        state = MarkerState(
                            position = LatLng(
                                stopState.busStop.StopLat.toDouble(),
                                stopState.busStop.StopLng.toDouble()
                            )
                        ),
                        title = stopState.busStop.StopDescr,
                        snippet = stopState.busStop.StopStreet ?: "",
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
    }
}

private suspend fun CameraPositionState.centerOnLatLng(
    coords: LatLng
) = animate(
    update = CameraUpdateFactory.newLatLngZoom(
        coords,
        17f
    ),
)


@Preview(name = "StopArrivalScreen")
@Composable
private fun PreviewStopArrivalScreen() {
    StopArrivalScreen(stopCode = "60991", stopDesc = "Test")
}