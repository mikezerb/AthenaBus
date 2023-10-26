package com.example.athenabus.presentation.bus_list

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.runtime.mutableStateListOf
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
import com.example.athenabus.presentation.bus_list.components.BusLineList
import com.example.athenabus.presentation.bus_list.components.ChangeLayoutButton
import com.example.athenabus.presentation.bus_list.components.ShowFilterButton
import com.example.athenabus.presentation.bus_list.components.SingleLineFilters


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewBusLineListScreen(
    navController: NavController,
    viewModel: BusLineListViewModel = hiltViewModel(),
) {
    val state = viewModel.state.value
    var isGridViewEnabled by rememberSaveable { mutableStateOf(false) }
    var isShowFilterEnabled by rememberSaveable { mutableStateOf(false) }
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

    val singleFilters = listOf(
        stringResource(id = R.string.buses_chip_label),
        stringResource(id = R.string.trolley_chip_label),
    )
    val multiFilters = listOf(
        stringResource(id = R.string._24hour_chip_label),
        stringResource(id = R.string.night_chip_label),
        stringResource(id = R.string.aeroplane_chip_label),
        stringResource(id = R.string.express_chip_label),
    )

    val allFilterLabels = listOf(
        stringResource(id = R.string.buses_chip_label),
        stringResource(id = R.string.trolley_chip_label),
        stringResource(id = R.string._24hour_chip_label),
        stringResource(id = R.string.night_chip_label),
        stringResource(id = R.string.aeroplane_chip_label),
        stringResource(id = R.string.express_chip_label),
    )

    val selectedMultiFilters = remember {
        mutableStateListOf("")
    }
    val selectedSingleFilter = remember {
        mutableStateListOf<String>() // initially, first item is selected
    }

    var lineFilter = remember {
        mutableStateListOf("")
    }

    var selectedFilter by remember {
        mutableStateOf("") // initially, first item is selected
    }

    val searchedList = remember(
        state.busLines,
        searchQuery.value,
        selectedFilter
    ) {
        state.busLines.filter { busLine ->
            val lineId = busLine.LineID
            val startsWithSearchQuery = lineId.startsWith(searchQuery.value, true)

            if (selectedFilter.contains(singleFilters[0])) {
                startsWithSearchQuery && !trolleyList.contains(lineId)
            } else if (selectedFilter.contains(singleFilters[1])) {
                startsWithSearchQuery && trolleyList.contains(lineId)
            } else if (selectedFilter.contains(multiFilters[0])) {
                startsWithSearchQuery && h24List.contains(lineId)
            } else if (selectedFilter.contains(multiFilters[1])) {
                startsWithSearchQuery && nightList.contains(lineId)
            } else if (selectedFilter.contains(multiFilters[2])) {
                startsWithSearchQuery && aeroplaneList.contains(lineId)
            } else if (selectedFilter.contains(multiFilters[3])) {
                startsWithSearchQuery && expressList.contains(lineId)
            } else {
                startsWithSearchQuery
            }
        }
    }

    Surface {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            DockedSearchBar(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
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
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            ShowFilterButton(showFilterView = isShowFilterEnabled,
                                onClick = {
                                    selectedFilter = "" // remove selected filters
                                    isShowFilterEnabled = !isShowFilterEnabled
                                }
                            )
                            ChangeLayoutButton(
                                enableGridView = isGridViewEnabled,
                                onClick = { onToggleGridView(!isGridViewEnabled) }
                            )
                        }
                    }
                }
            )
            { }
            AnimatedVisibility(
                visible = isShowFilterEnabled,
                enter = expandVertically(
                    expandFrom = Alignment.Top,
                    animationSpec = tween(durationMillis = 100)
                ) + fadeIn(
                    initialAlpha = 0.3f, animationSpec = tween(100)
                ),
                exit = shrinkVertically(
                    shrinkTowards = Alignment.Top,
                    animationSpec = tween(durationMillis = 100)
                ) + fadeOut(
                    animationSpec = tween(100)
                )
            ) {
                Column {
                    SingleLineFilters(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp),
                        selected = selectedFilter,
                        labels = singleFilters,
                        onClick = { item ->
                            selectedFilter = if (selectedFilter == item) "" else item
                        }
                    )
                    SingleLineFilters(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp),
                        selected = selectedFilter,
                        labels = multiFilters,
                        onClick = { item ->
                            selectedFilter = if (selectedFilter == item) "" else item
                        }
                    )
                }
            }
            BusLineList(
                modifier = Modifier.fillMaxSize(),
                navController = navController,
                isGridLayout = isGridViewEnabled,
                lines = searchedList,
                search = searchQuery.value,
            )
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
