package com.example.athenabus.presentation.line_details.components.tabs

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.athenabus.domain.model.Line
import com.example.athenabus.domain.model.Route
import com.example.athenabus.presentation.line_details.RouteStopsViewModel
import com.example.athenabus.presentation.line_details.components.AutoCompleteTextField
import com.example.athenabus.presentation.line_details.components.StopListItem

@Composable
fun StopsScreen(
    modifier: Modifier = Modifier,
    line: Line,
    routes: List<Route>,
    viewModel: RouteStopsViewModel = hiltViewModel(),
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedRoute by remember {
        mutableStateOf("")
    }
    var selectedRouteCode by remember {
        mutableStateOf("")
    }
    val context = LocalContext.current
    val state = viewModel.state.value
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(4.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        val options: MutableList<String> = mutableListOf()

        LaunchedEffect(key1 = Unit) {
            if (routes.isNotEmpty()) {
                routes.forEach { route ->
                    options += route.RouteDescr
                }
            }
        }

        AutoCompleteTextField(
            routes = routes,
            initialText = "Choose direction",
            onDismiss = { expanded = false },
            onClick = { expanded = true },
            onSelect = { i ->
                selectedRoute = i.RouteDescr
                selectedRouteCode = i.RouteCode
                expanded = false
                viewModel.getStops(selectedRouteCode)
            },
            selectedOption = selectedRoute,
            expanded = expanded,
            onExpanded = { expanded = it },
        )

        if (state.isLoading) {
            CircularProgressIndicator()
        } else if (state.error.isNotEmpty()) {
            Text(text = state.error, style = MaterialTheme.typography.headlineMedium)
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(0.dp)
            ) {
                items(state.stops) { stop ->
                    StopListItem(
                        stop = stop,
                        onItemClick = {
                            Toast.makeText(
                                context,
                                "Clicked ${stop.StopDescr}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    )
                    HorizontalDivider()
                }
            }
        }
    }
}
