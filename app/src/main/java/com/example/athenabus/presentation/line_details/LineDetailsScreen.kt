package com.example.athenabus.presentation.line_details
import android.util.Log
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.DirectionsBus
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Timeline
import androidx.compose.material.icons.outlined.DirectionsBus
import androidx.compose.material.icons.outlined.StarBorder
import androidx.compose.material.icons.outlined.Timeline
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SecondaryTabRow
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.athenabus.R
import com.example.athenabus.presentation.common.TabItem
import com.example.athenabus.presentation.line_details.components.tabs.ScheduleScreen
import com.example.athenabus.presentation.line_details.components.tabs.StopsScreen
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun LineDetailsScreen(
    modifier: Modifier = Modifier,
    navController: NavController = rememberNavController(),
    lineId: String = "",
    lineCode: String = "",
    lineDesc: String = "",
    isFav: Boolean = false,
    viewModel: LineDetailsViewModel = hiltViewModel(),
    routeViewModel: RouteDetailsViewModel = hiltViewModel(),
) {
    Log.d(
        "LineDetailsScreen",
        "LineID: $lineId, LineCode: $lineCode, lineDesc: $lineDesc isFav = $isFav"
    )
    val state = viewModel.state.value
    val routeState = routeViewModel.state.value

    val checkedState = remember { mutableStateOf(state.line?.isFavorite) }
    var checked by remember {
        mutableStateOf(
            state.line?.isFavorite ?: false
        ) // initially checked, default to false if null
    }

    LaunchedEffect(true, key2 = lineId) {
        viewModel.getLine(lineId)
    }
    LaunchedEffect(key1 = true, key2 = lineId) {
        routeViewModel.getLineCodes(lineId)
    }

    LaunchedEffect(key1 = true, key2 = lineId) {
        checked = viewModel.isFavoriteLine(lineId)
    }

    val tabItems = listOf(
        TabItem(
            title = stringResource(R.string.stops_tab_title),
            selectedIcon = Icons.Filled.DirectionsBus,
            unSelectedIcon = Icons.Outlined.DirectionsBus,
            screen = {
                state.line?.let {
                    StopsScreen(
                        routes = routeState.availableRoutes
                    )
                }
            }
        ),
        TabItem(
            title = stringResource(R.string.schedule_tab_title),
            selectedIcon = Icons.Filled.Timeline,
            unSelectedIcon = Icons.Outlined.Timeline,
            screen = { state.line?.let { ScheduleScreen(line = it) } }
        )
    )
    val context = LocalContext.current
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        modifier = Modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection)
            .fillMaxSize(),
        topBar = {
            LargeTopAppBar(
                title = {
                    Column {
                        Text(text = state.line?.LineID ?: "")
                        Text(
                            text = state.line?.LineDescr ?: "",
                            maxLines = 1,
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { }) {
                        Icon(imageVector = Icons.Default.Map, contentDescription = null)
                    }
                    IconToggleButton(
                        checked = checked,
                        onCheckedChange = { _checked ->
                            scope.launch {
                                if (_checked) {
                                    Toast.makeText(
                                        context,
                                        "Added to favorites",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    state.line?.let { viewModel.addFavoriteLine(it) }
                                } else {
                                    Toast.makeText(
                                        context,
                                        "Removed from favorites",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    state.line?.let { viewModel.removeFavoriteLine(lineId) }
                                }
                            }
                            checked = _checked
                            checkedState.value = _checked
                        }) {
                        Icon(
                            imageVector = if (checked) Icons.Default.Star else Icons.Outlined.StarBorder,
                            contentDescription = null
                        )
                    }
                    IconButton(
                        onClick = {
                            Toast.makeText(
                                context,
                                "More settings",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    ) {
                        Icon(imageVector = Icons.Default.MoreVert, contentDescription = null)
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
            )
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
    ) {
        AnimatedVisibility(
            visible = state.line != null,
            enter = slideInVertically(),
            exit = slideOutVertically()
        ) {
            Surface(modifier = Modifier.padding(it)) {
                var selectedTabIndex by remember { mutableIntStateOf(0) }
                val pagerState = rememberPagerState { tabItems.size }

                LaunchedEffect(selectedTabIndex) {
                    pagerState.animateScrollToPage(selectedTabIndex)
                }

                LaunchedEffect(pagerState.currentPage) {
                    selectedTabIndex = pagerState.currentPage
                }

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    SecondaryTabRow(selectedTabIndex = selectedTabIndex) {
                        tabItems.forEachIndexed { index, tabItem ->
                            Tab(
                                text = {
                                    Text(
                                        text = tabItem.title,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                },
                                selected = index == selectedTabIndex,
                                onClick = {
                                    selectedTabIndex = index
                                },
                            )
                        }
                    }
                    HorizontalPager(
                        state = pagerState,
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    ) { index ->
                        if (routeState.isLoading) {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                LinearProgressIndicator(
                                    modifier = Modifier.align(Alignment.Center)
                                )
                            }
                        } else {
                            tabItems[index].screen()    // [pagerState.currentPage]
                        }
                    }
                }
                AnimatedVisibility(
                    visible = state.isLoading,
                    modifier = Modifier.fillMaxSize(),
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .size(54.dp),
                        strokeWidth = 6.dp
                    )
                }
            }
        }
    }
}

@Preview(name = "LineDetailsScreen")
@Composable
private fun PreviewLineDetailsScreen() {
    LineDetailsScreen()
}
