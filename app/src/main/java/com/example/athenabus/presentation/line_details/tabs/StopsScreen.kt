package com.example.athenabus.presentation.line_details.tabs

import DropdownMenuSelection
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.athenabus.R
import com.example.athenabus.domain.model.Route
import com.example.athenabus.presentation.line_details.LineDetailsViewModel
import com.example.athenabus.presentation.line_details.components.BusStopItems

@Composable
fun StopsScreen(
    modifier: Modifier = Modifier,
    routes: List<Route>,
    viewModel: LineDetailsViewModel = hiltViewModel(),
    navController: NavController = rememberNavController()
) {
    var expanded by remember { mutableStateOf(false) }

    var selectedRoute by remember {
        mutableStateOf(routes.first().RouteDescr)
    }

    var selectedRouteCode by remember {
        mutableStateOf(routes.first().RouteCode)
    }
    val state = viewModel.stopState.value

    LaunchedEffect(key1 = selectedRoute) {
        viewModel.getStops(selectedRouteCode)
    }

    Column(
        modifier = modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        DropdownMenuSelection(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            itemList = routes.map { it.RouteDescr },
            initialText = stringResource(id = R.string.choose_direction),
            onDismiss = { expanded = false },
            onClick = { route, i ->
                selectedRoute = route
                selectedRouteCode = routes[i].RouteCode
                expanded = !expanded
                viewModel.getStops(selectedRouteCode)
            },
            selectedOption = selectedRoute,
            expanded = expanded,
            onExpanded = { expanded = it },
        )

        if (state.isLoading) {
            CircularProgressIndicator()
        } else if (state.error.isNotEmpty()) {
            Text(
                text = state.error,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.error
            )
        } else {
            BusStopItems(
                items = state.stops,
                onStopClick = { stop ->
                    navController.navigate(
                        com.example.athenabus.presentation.navigation.Route.StopActivity.route + "?stopCode=${stop.StopCode}&stopDesc=${stop.StopDescr}&stopLat=${stop.StopLat}&stopLng=${stop.StopLng}"
                    )
                }
            )
        }
    }
}
