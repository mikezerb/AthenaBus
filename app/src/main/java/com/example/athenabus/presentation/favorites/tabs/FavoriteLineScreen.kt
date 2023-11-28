package com.example.athenabus.presentation.favorites.tabs

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.athenabus.R
import com.example.athenabus.domain.model.Line
import com.example.athenabus.presentation.bus_list.components.BusLineListItem
import com.example.athenabus.presentation.common.EmptyScreen
import com.example.athenabus.presentation.navigation.Route

@Composable
fun FavoriteLineScreen(
    modifier: Modifier = Modifier,
    lines: List<Line> = emptyList(),
    navController: NavController = rememberNavController(),
) {
    Log.d("FavoriteLineScreen", "lines: ${lines.size}")

    if (lines.isEmpty()) {
        EmptyScreen(title = stringResource(R.string.no_favorite_lines_found))
    } else {
        Column(modifier) {
            LazyColumn {
                items(lines) { line ->
                    BusLineListItem(
                        busLine = line,
                        onItemClick =
                        {
                            navController.navigate(
                                Route.LineDetailsActivity.route +
                                        "?lineId=${line.LineID}&lineCode=${line.LineCode}&lineDesc=${line.LineDescr}&isFav=${line.isFavorite}"
                            )
                        },
                        onToggleFavorite = { }
                    )
                }
            }
        }
    }

}

@Preview(name = "FavoriteLineScreen")
@Composable
private fun PreviewFavoriteLineScreen() {
    FavoriteLineScreen()
}