package com.example.athenabus.presentation.bus_list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.athenabus.presentation.bus_list.components.BusLineItem
import com.example.athenabus.presentation.bus_list.components.LinesSearchBar

@Composable
fun NewBusLineListScreen(
    navController: NavController,
    viewModel: BusLineListViewModel = hiltViewModel(),
) {
    val state = viewModel.state.value

    // Create a MutableState to store the search query
    val searchQuery = rememberSaveable { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        LinesSearchBar(
            searchQuery = searchQuery.value,
            onSearchChange = {
                searchQuery.value = it
                //viewModel.searchBusLines(it)
            },
            onClearSearch = {
                if (searchQuery.value.isNotEmpty())
                    searchQuery.value = ""
                //viewModel.searchBusLines(searchQuery.value)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
        )
        Spacer(modifier = Modifier.height(6.dp))
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
        ) {
            items(state.bus_lines.filter { it.LineID.contains(searchQuery.value, true) }) { line ->
                BusLineItem(
                    busLine = line, onItemClick =
                    {
                        //navController.navigate(Route.BusRoutesScreen.route + "/${line.LineCode}")
                    },
                    onToggleFavorite = { _ ->

                    }
                )
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