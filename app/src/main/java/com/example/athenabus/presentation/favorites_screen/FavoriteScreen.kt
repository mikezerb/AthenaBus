package com.example.athenabus.presentation.favorites_screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DirectionsBus
import androidx.compose.material.icons.filled.FollowTheSigns
import androidx.compose.material.icons.outlined.DirectionsBus
import androidx.compose.material.icons.outlined.FollowTheSigns
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.athenabus.R
import com.example.athenabus.presentation.common.TabItem

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FavoriteScreen(
    navController: NavController
) {

    val favTabItems = listOf(
        TabItem(
            title = stringResource(R.string.favourite_lines_title),
            selectedIcon = Icons.Filled.DirectionsBus,
            unSelectedIcon = Icons.Outlined.DirectionsBus
        ),
        TabItem(
            title = stringResource(R.string.stops_tab_title),
            selectedIcon = Icons.Filled.FollowTheSigns,
            unSelectedIcon = Icons.Outlined.FollowTheSigns
        )
    )
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {

        var selectedTabIndex by remember { mutableStateOf(0) }
        val pagerState = rememberPagerState { favTabItems.size }

        LaunchedEffect(selectedTabIndex) {
            pagerState.animateScrollToPage(selectedTabIndex)
        }

        LaunchedEffect(pagerState.currentPage) {
            // Used for more than two pages
//                if (!pagerState.isScrollInProgress) {
//                    selectedTabIndex = pagerState.currentPage
//                }
            selectedTabIndex = pagerState.currentPage

        }
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            PrimaryTabRow(selectedTabIndex = selectedTabIndex) {
                favTabItems.forEachIndexed { index, tabItem ->
                    Tab(
                        selected = index == selectedTabIndex,
                        onClick = { selectedTabIndex = index },
                        text = {
                            Text(
                                text = tabItem.title, maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        },
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
                    Text(text = favTabItems[index].title)
                    DropdownMenu(expanded = false, onDismissRequest = { /*TODO*/ }) {

                    }
                }
            }
        }

    }

}

@Preview(name = "FavoriteScreen")
@Composable
private fun PreviewFavoriteScreen() {
    FavoriteScreen(navController = rememberNavController())
}
