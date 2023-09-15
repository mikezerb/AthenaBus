package com.example.athenabus.presentation.bus_detail.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.athenabus.domain.model.Route

@Composable
fun RouteList(
    routes: List<Route>,
    onRouteSelected: (Route) -> Unit
) {
    LazyColumn {
        items(routes) { route ->
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        onRouteSelected(route)
                    }
                    .padding(16.dp)
            ) {
                Text(
                    text = route.route_descr,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            Divider()
        }
    }

}