package com.example.athenabus.presentation.bus_list


import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.athenabus.R
import com.example.athenabus.presentation.Screen
import com.example.athenabus.presentation.bus_list.components.BusLineItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BusLineListScreen(
    navController: NavController,
    viewModel: BusLineListViewModel = hiltViewModel()
) {

    val state = viewModel.state.value
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.app_name))
                },
                navigationIcon = {
                    // You can add a navigation icon if needed
                }
            )
        },
        content = {

            LazyColumn(modifier = Modifier.fillMaxSize().padding(it)) {
                items(state.bus_lines) { line ->
                    BusLineItem(bus_line = line, onItemClick =
                    {
                        navController.navigate(Screen.BusLineDetailsScreen.route + "/${line.LineID}")
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
                CircularProgressIndicator()
            }
        }
    );
    {
    }
}