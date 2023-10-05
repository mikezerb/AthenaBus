package com.example.athenabus.presentation.bus_list

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DockedSearchBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.athenabus.R
import com.example.athenabus.presentation.bus_list.components.BusLineItem
import com.example.athenabus.presentation.bus_list.components.ChangeLayoutButton
import com.example.athenabus.presentation.bus_list.components.GridBusLineItem
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewBusLineListScreen(
    navController: NavController,
    viewModel: BusLineListViewModel = hiltViewModel(),
) {
    val state = viewModel.state.value
    var isGridViewEnabled by rememberSaveable { mutableStateOf(false) }
    val onToggleGridView: (Boolean) -> Unit = { isEnabled ->
        isGridViewEnabled = isEnabled
        // Update your UI or data to switch between grid and list view.
    }
    // Create a MutableState to store the search query
    val searchQuery = rememberSaveable { mutableStateOf("") }
    val scope = rememberCoroutineScope()
    Surface {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            DockedSearchBar(
                modifier = Modifier
                    .align(Alignment.Start)
                    .fillMaxWidth()
                    .padding(8.dp),
                query = searchQuery.value,
                onQueryChange = {
                    searchQuery.value = it
                },
                onSearch = {},
                active = false,
                onActiveChange = {},
                placeholder = { Text(text = stringResource(id = R.string.search_place_holder)) },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Outlined.Search,
                        contentDescription = stringResource(id = R.string.search_place_holder),
                        tint = MaterialTheme.colorScheme.surfaceTint
                    )
                },
                trailingIcon = {
                    if (searchQuery.value.isNotEmpty()) {
                        IconButton(
                            onClick = {
                                searchQuery.value = ""
                            },
                            modifier = Modifier.padding(horizontal = 12.dp)
                        ) {
                            Icon(imageVector = Icons.Outlined.Close, contentDescription = null)
                        }
                    } else {
                        ChangeLayoutButton(
                            enableGridView = isGridViewEnabled,
                            onClick = { onToggleGridView(!isGridViewEnabled) })
                    }
                }
            )
            { }
            if (isGridViewEnabled) {
                LazyVerticalStaggeredGrid(
                    columns = StaggeredGridCells.Fixed(2),
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(0.dp, 5.dp, 0.dp, 0.dp)
                ) {
                    items(
                        state.bus_lines.filter { it.LineID.startsWith(searchQuery.value, true) }
                            .sortedBy { it.LineID }
                    ) { line ->
                        GridBusLineItem(
                            busLine = line, onItemClick =
                            {
                                //navController.navigate(Route.BusRoutesScreen.route + "/${line.LineCode}")
                            },
                            onToggleFavorite = { _ ->
                                scope.launch {
                                    viewModel.toggleFavoriteLine(query = line.LineID)
                                }
                            }
                        )
                    }
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    items(
                        state.bus_lines.filter { it.LineID.startsWith(searchQuery.value, true) }
                            .sortedBy { it.LineID }
                    ) { line ->
                        BusLineItem(
                            busLine = line, onItemClick =
                            {
                                //navController.navigate(Route.BusRoutesScreen.route + "/${line.LineCode}")
                            },
                            onToggleFavorite = { _ ->
                                scope.launch {
                                    viewModel.toggleFavoriteLine(query = line.LineID)
                                }
                            }
                        )
                    }
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
                        .align(Alignment.CenterHorizontally),
                    strokeWidth = 6.dp
                )
            }
        }
    }
}