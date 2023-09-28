package com.example.athenabus.presentation.search_screen

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.athenabus.R
import com.example.athenabus.presentation.common.MaterialTopAppBar
import com.example.athenabus.presentation.search_screen.components.LineItem

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    viewModel: SearchLinesViewModel = hiltViewModel(),
    navController: NavController
) {

    val state = viewModel.state
    var text by remember { mutableStateOf("") }
    var active by remember { mutableStateOf(false) }
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        modifier = Modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection)
            .fillMaxSize(),
        topBar = {
            MaterialTopAppBar(
                title = "Αναζήτηση γραμμών",
                scrollBehavior = scrollBehavior,
                enableBackButton = true,
                onBackClick = { navController.navigateUp() }

            )
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
    ) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            SearchBar(
                query = state.searchQuery,
                onQueryChange = { viewModel.onEvent(SearchLinesEvent.OnSearchQueryChange(it)) },
                onSearch = { newQuery ->
                    viewModel.onEvent(
                        SearchLinesEvent.OnSearchQueryChange(
                            newQuery
                        )
                    )
                },
                active = active,
                onActiveChange = { active = it },
                placeholder = { Text(text = stringResource(R.string.search_placeholder)) },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = null
                    )
                },
                trailingIcon = {
                    if (active) {
                        IconButton(
                            onClick = {
                                if (state.searchQuery.isNotEmpty()) state.searchQuery =
                                    "" else active =
                                    false
                            }
                        ) { Icon(imageVector = Icons.Filled.Close, contentDescription = null) }
                    } else null
                },
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
            ) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(state.lines.size) { i ->
                        val line = state.lines[i]
                        LineItem(
                            line = line,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    //TODO: Navigate to details

                                }
                                .padding(16.dp)
                        )


                        if (i < state.lines.size) {
                            Divider(modifier = Modifier.padding(horizontal = 16.dp))
                        }

                    }

                }


            }

        }
    }

}