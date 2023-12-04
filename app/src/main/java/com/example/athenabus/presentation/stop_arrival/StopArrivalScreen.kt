package com.example.athenabus.presentation.stop_arrival

import android.widget.Toast
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
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
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
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
    stopViewModel: StopViewModel = hiltViewModel(),
    themeViewModel: ThemeViewModel = hiltViewModel(),
    stopCode: String,
    stopDesc: String,
    stopLat: String,
    stopLng: String,
) {

    val state = viewModel.state.value
    val stopArrivalState = stopArrivalViewModel.state.value
    val stopState = stopViewModel.state.value
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val darkThemeState by themeViewModel.themeState.collectAsState()
//
//    LaunchedEffect(key1 = true, key2 = stopCode) {
//        stopArrivalViewModel.getStopDetails(stopCode)
//    }

    LaunchedEffect(key1 = true) {
        stopArrivalViewModel.getStopArrivals(stopCode)
    }

    LaunchedEffect(key1 = true) {
        stopViewModel.getRoutesForStop(stopCode)
    }

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


    var isMapLoaded by remember { mutableStateOf(false) }

    val uiSettings by remember {
        mutableStateOf(
            MapUiSettings(
                compassEnabled = true,
                myLocationButtonEnabled = true,
            )
        )
    }

    val stopCoords = LatLng(stopLat.toDouble(), stopLng.toDouble())
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(stopCoords, 18f)
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
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 4.dp)
                    .sizeIn(maxHeight = 500.dp)
                    .defaultMinSize(minHeight = 150.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (stopArrivalState.stopArrivals.isEmpty()) {
                    Text(
                        text = "No Incoming Buses",
                        style = MaterialTheme.typography.titleLarge
                    )
                } else {
                    Text(
                        modifier = Modifier.padding(bottom = 12.dp),
                        text = "Incoming Buses",
                        style = MaterialTheme.typography.titleLarge
                    )
                    HorizontalDivider()
                    if (stopArrivalState.stopArrivals.isNotEmpty()) {
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 8.dp)
                        ) {
                            items(stopArrivalState.stopArrivals) { item ->
                                val lineId =
                                    stopState.routeStops.find { it.RouteCode == item.route_code }?.LineID
                                        ?: ""
                                val lineDesc =
                                    stopState.routeStops.find { it.RouteCode == item.route_code }?.LineDescr
                                        ?: ""
                                ListItem(
                                    headlineContent = {
                                        Text(
                                            text = lineId,
                                            style = MaterialTheme.typography.titleLarge,
                                        )
                                    },
                                    supportingContent = {
                                        Text(
                                            text = lineDesc,
                                            overflow = TextOverflow.Ellipsis,
                                            maxLines = 2
                                        )
                                    },
                                    trailingContent = {
                                        Text(
                                            text = item.btime2 + " " + pluralStringResource(
                                                id = R.plurals.minutes_arrive,
                                                count = item.btime2.toInt()
                                            )
                                        )
                                    }
                                )
                            }
                        }
                    } else if (stopArrivalState.isLoading) {
                        CircularProgressIndicator()
                    } else if (stopArrivalState.error.isNotEmpty()) {
                        Text(text = stopArrivalState.error)
                    }
                }
            }
        }
    ) {
        Box(
            Modifier
                .fillMaxSize()
                .padding(it)
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
//                LaunchedEffect(key1 = isMapLoaded) {
//                    cameraPositionState.centerOnLatLng(
//                        LatLng(
//                            stopLat.toDouble(),
//                            stopLng.toDouble()
//                        )
//                    )
//                }
                MarkerComposable(
                    state = MarkerState(
                        position = LatLng(
                            stopLat.toDouble(),
                            stopLng.toDouble()
                        )
                    ),
                    title = stopDesc,
                    draggable = false,
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.bus_stop_new),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.surfaceTint
                    )
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
        19f
    ),
)
