package com.example.athenabus.presentation.route_list

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.athenabus.presentation.route_list.components.BusRouteItem

@Composable
fun RouteListScreen(
    navController: NavController,
    viewModel: RouteListViewModel = hiltViewModel()
) {
    var showDialog by remember { mutableStateOf(false) }
    val state = viewModel.state.value
    Dialog(onDismissRequest = { showDialog = false }) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(375.dp)
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),

            ) {
            Text(
                text = "Select a Route",
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(12.dp)
            )
            HorizontalDivider(modifier = Modifier.padding(bottom = 4.dp), thickness = .8.dp)
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(state.busRoutes) { bus_route ->
                    BusRouteItem(busRoute = bus_route, onItemClick =
                    {

                    })
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