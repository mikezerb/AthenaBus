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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.athenabus.R
import com.example.athenabus.domain.model.Line
import com.example.athenabus.presentation.bus_list.components.BusLineItem
import com.example.athenabus.presentation.navigation.Route
import com.example.athenabus.presentation.route_list.RouteListViewModel
import com.example.athenabus.presentation.route_list.components.BusRouteItem
import com.example.athenabus.ui.theme.AthenaBusTheme

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
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                ),
                modifier = Modifier
                    .wrapContentSize()
                    .requiredHeightIn(min = 130.dp)
                    .padding(12.dp),
                shape = RoundedCornerShape(12.dp),
            ) {
                Text(
                    text = stringResource(R.string.select_route_title),
                    modifier = Modifier
                        .align(Alignment.Start)
                        .padding(12.dp),
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )
                HorizontalDivider(
                    modifier = Modifier
                        .padding(bottom = 4.dp)
                        .padding(horizontal = 4.dp),
                    thickness = 1.5.dp,
                    color = MaterialTheme.colorScheme.primary
                )
//                // Check if busRoutes length is 1 and navigate if true
//                if (route_state.busRoutes.size == 1) {
//                    showDialog = false
//                    navController.navigate(Route.RoutesDetailScreen.route)
//                }
                if (route_state.error.isNotBlank()) {
                    Text(
                        text = state.error,
                        color = MaterialTheme.colorScheme.error,
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp)
                    )
                } else if (route_state.isLoading) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 12.dp)
                            .padding(horizontal = 8.dp),

                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        items(route_state.busRoutes) { busRoute ->
                            BusRouteItem(
                                busRoute = busRoute,
                                onItemClick =
                                {
                                    showDialog = false
                                    navController.navigate(Route.RoutesDetailScreen.route)
                                }
                            )
                            HorizontalDivider(
                                thickness = .8.dp,
                                modifier = Modifier.padding(horizontal = 10.dp),
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }
            }
        }
    }
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.TopCenter) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
        ) {
            items(state.bus_lines) { line ->
                BusLineItem(
                    busLine = line, onItemClick =
                    {
                        //navController.navigate(Route.BusRoutesScreen.route + "/${line.LineCode}")
                        showDialog = true
                        selectedBusLine = line
                        // Pass the line id to the RouteListViewModel
                        routeViewModel.getLineCodes(line.LineID)
//                          scope.launch {
//                            snackbarHostState.showSnackbar(
//                                message = "Επιλογή γραμμής: " + line.LineCode,
//                                withDismissAction = true,
//                                duration = SnackbarDuration.Short
//                            )
//                        }
                    },
                    onToggleFavorite = { _ ->

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

@Preview
@Composable
fun BuslineListPreview() {
    AthenaBusTheme {
        BusLineListScreen(navController = rememberNavController())
    }
}


