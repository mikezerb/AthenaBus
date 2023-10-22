package com.example.athenabus.presentation.bus_list

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DockedSearchBar
import androidx.compose.material3.ElevatedFilterChip
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.athenabus.R
import com.example.athenabus.domain.model.Line
import com.example.athenabus.presentation.bus_list.components.BusLineListItem
import com.example.athenabus.presentation.bus_list.components.ChangeLayoutButton
import com.example.athenabus.presentation.bus_list.components.GridBusLineItem
import com.example.athenabus.presentation.navigation.Route
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
    }
    // Create a MutableState to store the search query
    val searchQuery = rememberSaveable { mutableStateOf("") }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    var trolleySelected by remember { mutableStateOf(false) }
    var busesSelected by remember { mutableStateOf(false) }
    var nightSelected by remember { mutableStateOf(false) }
    var n24Selected by remember { mutableStateOf(false) }
    var expressSelected by remember { mutableStateOf(false) }
    var aeroplaneSelected by remember { mutableStateOf(false) }
    val trolleyList: List<String> = listOf(
        "10", "11", "12", "15",
        "16", "17", "18", "19", "19Β", "20", "21", "24", "25"
    )
    val h24List: List<String> = listOf("040", "11")
    val nightList: List<String> = listOf("040", "11", "500", "790", "Χ14")
    val aeroplaneList: List<String> = listOf("Χ93", "Χ95", "Χ96", "Χ97")
    val expressList: List<String> = listOf("Ε14", "Ε90", "Χ14")

    fun filterLines(): List<Line> {
        return when {
            trolleySelected -> state.bus_lines.filter { trolleyList.contains(it.LineID) }
                .filter { it.LineID.startsWith(searchQuery.value, true) }

            busesSelected -> state.bus_lines.filter { !trolleyList.contains(it.LineID) }
                .filter { it.LineID.startsWith(searchQuery.value, true) }

            n24Selected -> state.bus_lines.filter { h24List.contains(it.LineID) }
                .filter { it.LineID.startsWith(searchQuery.value, true) }

            nightSelected -> state.bus_lines.filter { nightList.contains(it.LineID) }
                .filter { it.LineID.startsWith(searchQuery.value, true) }

            aeroplaneSelected -> state.bus_lines.filter { aeroplaneList.contains(it.LineID) }
                .filter { it.LineID.startsWith(searchQuery.value, true) }

            expressSelected -> state.bus_lines.filter { expressList.contains(it.LineID) }
                .filter { it.LineID.startsWith(searchQuery.value, true) }

            else -> state.bus_lines.filter { it.LineID.startsWith(searchQuery.value, true) }
        }.sortedBy { it.LineID }
    }

    Surface {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
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
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
                    .horizontalScroll(
                        rememberScrollState()
                    ),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                ElevatedFilterChip(
                    modifier = Modifier.padding(horizontal = 4.dp),
                    onClick = { trolleySelected = !trolleySelected },
                    label = {
                        Text(stringResource(R.string.trolley_chip_label))
                    },
                    selected = trolleySelected,
                    leadingIcon = if (trolleySelected) {
                        {
                            Icon(
                                imageVector = Icons.Filled.Done,
                                contentDescription = "Done icon",
                                modifier = Modifier.size(FilterChipDefaults.IconSize)
                            )
                        }
                    } else {
                        null
                    },
                )
                ElevatedFilterChip(
                    modifier = Modifier.padding(horizontal = 4.dp),
                    onClick = { busesSelected = !busesSelected },
                    label = {
                        Text(stringResource(R.string.buses_chip_label))
                    },
                    selected = busesSelected,
                    leadingIcon = if (busesSelected) {
                        {
                            Icon(
                                imageVector = Icons.Filled.Done,
                                contentDescription = "Done icon",
                                modifier = Modifier.size(FilterChipDefaults.IconSize)
                            )
                        }
                    } else {
                        null
                    },
                )
                ElevatedFilterChip(
                    modifier = Modifier.padding(horizontal = 4.dp),
                    onClick = { n24Selected = !n24Selected },
                    label = {
                        Text(stringResource(R.string._24hour_chip_label))
                    },
                    selected = n24Selected,
                    leadingIcon = if (n24Selected) {
                        {
                            Icon(
                                imageVector = Icons.Filled.Done,
                                contentDescription = "Done icon",
                                modifier = Modifier.size(FilterChipDefaults.IconSize)
                            )
                        }
                    } else {
                        null
                    },
                )
                ElevatedFilterChip(
                    modifier = Modifier.padding(horizontal = 4.dp),
                    onClick = { nightSelected = !nightSelected },
                    label = {
                        Text(stringResource(R.string.night_chip_label))
                    },
                    selected = nightSelected,
                    leadingIcon = if (nightSelected) {
                        {
                            Icon(
                                imageVector = Icons.Filled.Done,
                                contentDescription = "Done icon",
                                modifier = Modifier.size(FilterChipDefaults.IconSize)
                            )
                        }
                    } else {
                        null
                    },
                )
                ElevatedFilterChip(
                    modifier = Modifier.padding(horizontal = 4.dp),
                    onClick = { aeroplaneSelected = !aeroplaneSelected },
                    label = {
                        Text(stringResource(R.string.aeroplane_chip_label))
                    },
                    selected = aeroplaneSelected,
                    leadingIcon = if (aeroplaneSelected) {
                        {
                            Icon(
                                imageVector = Icons.Filled.Done,
                                contentDescription = "Done icon",
                                modifier = Modifier.size(FilterChipDefaults.IconSize)
                            )
                        }
                    } else {
                        null
                    },
                )
                ElevatedFilterChip(
                    modifier = Modifier.padding(horizontal = 4.dp),
                    onClick = { expressSelected = !expressSelected },
                    label = {
                        Text(stringResource(R.string.express_chip_label))
                    },
                    selected = expressSelected,
                    leadingIcon = if (expressSelected) {
                        {
                            Icon(
                                imageVector = Icons.Filled.Done,
                                contentDescription = "Done icon",
                                modifier = Modifier.size(FilterChipDefaults.IconSize)
                            )
                        }
                    } else {
                        null
                    },
                )
            }
            if (isGridViewEnabled) {
                LazyVerticalStaggeredGrid(
                    columns = StaggeredGridCells.Fixed(2),
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(0.dp, 5.dp, 0.dp, 0.dp)
                ) {
                    items(filterLines()) { line ->
                        GridBusLineItem(
                            busLine = line, onItemClick =
                            {
                                navController.navigate(Route.LineDetailsActivity.route + "?lineId=${line.LineID}&lineCode=${line.LineCode}&lineDesc=${line.LineDescr}&isFav=${line.isFavorite}") // &lineDesc=${line.LineDescr}&isFav=${line.isFavorite}
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
                    items(filterLines()) { line ->
                        BusLineListItem(
                            busLine = line, onItemClick =
                            {
                                navController.navigate(Route.LineDetailsActivity.route + "?lineId=${line.LineID}&lineCode=${line.LineCode}&lineDesc=${line.LineDescr}&isFav=${line.isFavorite}") // &lineDesc=${line.LineDescr}&isFav=${line.isFavorite}
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


// Function to filter lines based on the selected category
