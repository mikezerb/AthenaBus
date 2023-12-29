package com.example.athenabus.presentation.favorites.tabs

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.athenabus.R
import com.example.athenabus.presentation.common.EmptyScreen
import com.example.athenabus.presentation.favorites.FavoriteScreenViewModel
import com.example.athenabus.presentation.favorites.components.FavoriteStopItem
import kotlinx.coroutines.launch

@Composable
fun FavoriteStopsScreen(
    viewModel: FavoriteScreenViewModel = hiltViewModel(),
    navController: NavController = rememberNavController(),
) {
    val state = viewModel.stopState.value

    val scope = rememberCoroutineScope()

    if (state.favoriteStops.isEmpty()) {
        EmptyScreen(title = stringResource(R.string.no_favorite_stops_found))
    } else if (state.isLoading) {
        CircularProgressIndicator()
    } else {
        Column(Modifier.fillMaxSize()) {
            LazyColumn {
                items(state.favoriteStops, key = { it.StopCode }) { stop ->
                    FavoriteStopItem(
                        modifier = Modifier.clickable {
                            navController.navigate(
                                com.example.athenabus.presentation.navigation.Route.StopActivity.route
                                        + "?stopCode=${stop.StopCode}&stopDesc=${stop.StopDescr}&stopLat=${stop.StopLat}&stopLng=${stop.StopLng}"
                            )
                        },
                        stop = stop,
                        routes = state.routesForStops[stop.StopCode]?.map { it.LineID },
                        onRouteClick = { lineId ->
                            navController.navigate(
                                com.example.athenabus.presentation.navigation.Route.LineDetailsActivity.route +
                                        "?lineId=${lineId}"
                            )
                        },
                        onDelete = { stopId ->
                            scope.launch {
                                viewModel.removeFavoriteStop(stopId)
                            }
                        }
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