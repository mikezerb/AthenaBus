package com.example.athenabus.presentation.route_details

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DirectionsBus
import androidx.compose.material.icons.filled.Timeline
import androidx.compose.material.icons.outlined.DirectionsBus
import androidx.compose.material.icons.outlined.Timeline
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.example.athenabus.R
import com.example.athenabus.presentation.common.TabItem

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun RouteDetailsScreen(
    navController: NavController,

    ) {
    val tabItems = listOf(
        TabItem(
            title = stringResource(R.string.stops_tab_title),
            selectedIcon = Icons.Filled.DirectionsBus,
            unSelectedIcon = Icons.Outlined.DirectionsBus
        ),
        TabItem(
            title = stringResource(R.string.schedule_tab_title),
            selectedIcon = Icons.Filled.Timeline,
            unSelectedIcon = Icons.Outlined.Timeline
        )
    )

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        var selectedTabIndex by remember { mutableStateOf(0) }
        val pagerState = rememberPagerState { tabItems.size }
        LaunchedEffect(selectedTabIndex) {
            pagerState.animateScrollToPage(selectedTabIndex)
        }

        LaunchedEffect(pagerState.currentPage, pagerState.isScrollInProgress) {
            if (!pagerState.isScrollInProgress) {    // for more than two pages
                selectedTabIndex = pagerState.currentPage
            }

        }
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            TabRow(selectedTabIndex = selectedTabIndex) {
                tabItems.forEachIndexed { index, tabItem ->
                    Tab(
                        selected = index == selectedTabIndex,
                        onClick = { selectedTabIndex = index },
                        text = { Text(text = tabItem.title) },
                        icon = {
                            Icon(
                                imageVector = if (index == selectedTabIndex) {
                                    tabItem.selectedIcon
                                } else tabItem.unSelectedIcon,
                                contentDescription = tabItem.title
                            )
                        }

                    )
                }

            }
            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) { index ->
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = tabItems[index].title)
                    DropdownMenu(expanded = false, onDismissRequest = { /*TODO*/ }) {

                    }
                }
            }

        }

    }

}