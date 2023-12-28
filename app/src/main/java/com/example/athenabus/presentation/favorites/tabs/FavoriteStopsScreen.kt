package com.example.athenabus.presentation.favorites.tabs

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.athenabus.R
import com.example.athenabus.domain.model.Route
import com.example.athenabus.domain.model.Stop
import com.example.athenabus.presentation.common.EmptyScreen
import com.example.athenabus.presentation.favorites.FavoriteStopsRouteViewModel
import com.example.athenabus.presentation.favorites.components.FavoriteStopItem

@Composable
fun FavoriteStopsScreen(
    modifier: Modifier = Modifier,
    stops: List<Stop> = emptyList(),
    routes: HashMap<String, List<Route>> = HashMap(),
    navController: NavController = rememberNavController(),
) {
    if (stops.isEmpty()) {
        EmptyScreen(title = stringResource(R.string.no_favorite_stops_found))
    } else {
        Column(modifier) {
            LazyColumn {
                items(stops) { stop ->
                    FavoriteStopItem(
                        modifier = Modifier.clickable {
                            navController.navigate(
                                com.example.athenabus.presentation.navigation.Route.StopActivity.route
                                        + "?stopCode=${stop.StopCode}&stopDesc=${stop.StopDescr}&stopLat=${stop.StopLat}&stopLng=${stop.StopLng}"
                            )
                        },
                        stop = stop,
                        routes = routes[stop.StopCode]?.map { it.LineID }
                    )
                }
            }
        }
    }
}

@Preview(name = "FavoriteStopsScreen")
@Composable
private fun PreviewFavoriteStopsScreen() {
    FavoriteStopsScreen()
}