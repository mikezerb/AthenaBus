package com.example.athenabus.presentation.bus_list.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.athenabus.R
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
) {
    if (lines.isEmpty() && search.isNotBlank()) {
        EmptyScreen(
            modifier = Modifier.fillMaxSize(),
            title = stringResource(R.string.cannot_find, search)
        )
    } else {
        AnimatedVisibility(
            visible = isGridLayout,
            enter = fadeIn(animationSpec = tween(300)),
            exit = fadeOut(animationSpec = tween(300))
        ) {
            LazyVerticalStaggeredGrid(
                columns = StaggeredGridCells.Fixed(2),
                modifier = modifier
            ) {
                items(
                    items = lines,
                    key = { it.LineCode }
                ) { line ->
                    GridBusLineItem(
                        busLine = line, onItemClick =
                        {
                            navController.navigate(Route.LineDetailsActivity.route + "?lineId=${line.LineID}")
                        },
                        onToggleFavorite = { }
                    )
                }
            }
        }
        AnimatedVisibility(
            visible = !isGridLayout,
            enter = fadeIn(animationSpec = tween(300)),
            exit = fadeOut(animationSpec = tween(300))
        ) {
            //Using LazyVerticalStaggeredGrid with 1 column because LazyColumn with filter crashes...
            LazyVerticalStaggeredGrid(
                columns = StaggeredGridCells.Fixed(1),
                modifier = modifier
            ) {
                items(
                    items = lines,
                    key = { it.LineCode }
                ) { line ->
                    BusLineListItem(
                        busLine = line,
                        onItemClick =
                        {
                            navController.navigate(
                                Route.LineDetailsActivity.route +
                                        "?lineId=${line.LineID}"
                            )
                        },
                        onToggleFavorite = { }
                    )
                }
            }
        }
    }
}