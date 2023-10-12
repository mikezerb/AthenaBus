package com.example.athenabus.presentation.line_details

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DirectionsBus
import androidx.compose.material.icons.filled.Timeline
import androidx.compose.material.icons.outlined.DirectionsBus
import androidx.compose.material.icons.outlined.Timeline
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.SecondaryTabRow
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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.athenabus.R
import com.example.athenabus.presentation.common.TabItem
import com.example.athenabus.presentation.line_details.components.AutoCompleteTextField

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LineDetailsScreen(
    modifier: Modifier = Modifier,
    navController: NavController = rememberNavController(),
    lineId: String = "",
    lineCode: String = "",
    lineDesc: String = "",
    isFav: Boolean = false,
    viewModel: LineDetailsViewModel = hiltViewModel()
) {
    Log.d(
        "LineDetailsScreen",
        "LineID: $lineId, LineCode: $lineCode, lineDesc: $lineDesc isFav = $isFav"
    )
    val state = viewModel.state.value

    LaunchedEffect(key1 = Unit) {
        viewModel.getLine(lineId)
    }

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

    AnimatedVisibility(
        visible = state.lines.isNotEmpty(),
        enter = slideInVertically(),
        exit = slideOutVertically()
    ) {
        Surface {

            var selectedTabIndex by remember { mutableStateOf(0) }
            val pagerState = rememberPagerState { tabItems.size }
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
                SecondaryTabRow(selectedTabIndex = selectedTabIndex) {
                    tabItems.forEachIndexed { index, tabItem ->
                        Tab(
                            text = { Text(text = tabItem.title, maxLines = 1, overflow = TextOverflow.Ellipsis) },
                            selected = index == selectedTabIndex,
                            onClick = { selectedTabIndex = index },
//                            icon = {
//                                Icon(
//                                    imageVector = if (index == selectedTabIndex) {
//                                        tabItem.selectedIcon
//                                    } else tabItem.unSelectedIcon,
//                                    contentDescription = tabItem.title
//                                )
//                            }
                        )
                    }
                }
                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) { index ->
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.TopCenter
                    ) {
                        val options =
                            listOf("Option 1", "Option 2", "Option 3", "Option 4", "Option 5")

                        AutoCompleteTextField(
                            initialText = stringResource(R.string.choose_direction),
                            itemList = options
                        )
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

@Preview(name = "LineDetailsScreen")
@Composable
private fun PreviewLineDetailsScreen() {
    LineDetailsScreen()
}
