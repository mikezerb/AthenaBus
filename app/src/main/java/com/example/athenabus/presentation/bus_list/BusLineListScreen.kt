package com.example.athenabus.presentation.bus_list


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeightIn
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.athenabus.R
import com.example.athenabus.domain.model.Line
import com.example.athenabus.presentation.bus_list.components.BusLineItem
import com.example.athenabus.presentation.nvgraph.Route
import com.example.athenabus.presentation.route_list.RouteListViewModel
import com.example.athenabus.presentation.route_list.components.BusRouteItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BusLineListScreen(
    navController: NavController,
    viewModel: BusLineListViewModel = hiltViewModel(),
    routeViewModel: RouteListViewModel = hiltViewModel()
) {

    val state = viewModel.state.value
    val route_state = routeViewModel.state.value

    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    var showDialog by remember { mutableStateOf(false) }

    var selectedBusLine: Line? by remember { mutableStateOf(null) }
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    if (showDialog && selectedBusLine != null) {
        Dialog(onDismissRequest = { showDialog = false }) {
            Card(
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 6.dp
                ),
                modifier = Modifier
                    .wrapContentSize()
                    .requiredHeightIn(min = 150.dp)
                    .padding(16.dp),
                shape = RoundedCornerShape(16.dp),
            ) {
                Text(
                    text = stringResource(R.string.select_route_title),
                    modifier = Modifier
                        .align(Alignment.Start)
                        .padding(12.dp),
                    style = MaterialTheme.typography.bodyLarge
                )
                Divider(
                    modifier = Modifier
                        .padding(bottom = 4.dp)
                        .padding(horizontal = 4.dp),
                    thickness = 1.4.dp,
                    color = MaterialTheme.colorScheme.onTertiaryContainer
                )
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 12.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    items(route_state.busRoutes) { bus_route ->
                        BusRouteItem(busRoute = bus_route, onItemClick =
                        {
                            showDialog = false
                            navController.navigate(Route.RoutesDetailScreen.route)
//                            scope.launch {
//                                snackbarHostState.showSnackbar(
//                                    message = "Επιλογή διαδρομής: " + bus_route.route_descr,
//                                    withDismissAction = true,
//                                    duration = SnackbarDuration.Short
//                                )
//                            }
                        })
                        Divider(
                            thickness = .7.dp,
                            modifier = Modifier.padding(horizontal = 12.dp),
                            color = MaterialTheme.colorScheme.onTertiaryContainer
                        )
                    }

                }
                if (state.error.isNotBlank()) {
                    Text(
                        text = state.error,
                        color = MaterialTheme.colorScheme.error,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp)
                    )
                }
                if (state.isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .size(54.dp),
                        strokeWidth = 6.dp
                    )
                }

            }
        }
    }
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.app_name))
                },
                navigationIcon = {
                    // You can add a navigation icon if needed
                },
                scrollBehavior = scrollBehavior,
                actions = {
                    IconButton(onClick = { navController.navigate(Route.SearchLineScreen.route) }) {
                        Icon(imageVector = Icons.Filled.Search, contentDescription = null)
                    }
                }
            )
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
    )
    {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.TopCenter) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
            ) {
                items(state.bus_lines) { line ->
                    BusLineItem(
                        busLine = line, onItemClick =
                        {
                            //navController.navigate(Route.BusRoutesScreen.route + "/${line.LineCode}")
                            showDialog = true
                            selectedBusLine = line
                            // Pass the line code to the RouteListViewModel
                            routeViewModel.getRoutes(line.LineCode)
//                        scope.launch {
//                            snackbarHostState.showSnackbar(
//                                message = "Επιλογή γραμμής: " + line.LineCode,
//                                withDismissAction = true,
//                                duration = SnackbarDuration.Short
//                            )
//                        }
                    }
                    )
                }
            }
            if (state.error.isNotBlank()) {
                Text(
                    text = state.error,
                    color = MaterialTheme.colorScheme.error,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                )
            }
            if (state.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .size(54.dp)
                        .align(Alignment.Center),
                    strokeWidth = 6.dp
                )
            }
        }
    }
}




