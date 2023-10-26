package com.example.athenabus.presentation.bus_list.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.athenabus.domain.model.Line
import com.example.athenabus.presentation.common.EmptyScreen
import com.example.athenabus.presentation.navigation.Route

@Composable
fun BusLineList(
    modifier: Modifier = Modifier,
    navController: NavController = rememberNavController(),
    isGridLayout: Boolean = false,
    lines: List<Line>,
    search: String,
    typeFilter: List<String>
) {
    val searchedList = remember(lines, search) {
        lines.filter { it.LineID.startsWith(search, true) }
    }
    if (lines.isEmpty()) {
        EmptyScreen(
            modifier = Modifier.fillMaxSize(),
            title = "Cannot find $search"
        )
    } else {
        AnimatedVisibility(
            visible = isGridLayout,
            enter = scaleIn(),
            exit = fadeOut()
        ) {
            LazyVerticalStaggeredGrid(
                columns = StaggeredGridCells.Fixed(2),
                modifier = modifier
            ) {
                items(searchedList) { line ->
                    GridBusLineItem(
                        busLine = line, onItemClick =
                        {
                            navController.navigate(Route.LineDetailsActivity.route + "?lineId=${line.LineID}&lineCode=${line.LineCode}&lineDesc=${line.LineDescr}&isFav=${line.isFavorite}") // &lineDesc=${line.LineDescr}&isFav=${line.isFavorite}
                        },
                        onToggleFavorite = { }
                    )
                }
            }
        }
        AnimatedVisibility(
            visible = !isGridLayout,
            enter = scaleIn(),
            exit = fadeOut()
        ) {
            //Using LazyVerticalStaggeredGrid with 1 column because LazyColumn with filter crashes...
            LazyVerticalStaggeredGrid(
                columns = StaggeredGridCells.Fixed(1),
                modifier = modifier
            ) {
                items(searchedList) { line ->
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

@Preview(name = "BusLineList")
@Composable
private fun PreviewBusLineList() {

}