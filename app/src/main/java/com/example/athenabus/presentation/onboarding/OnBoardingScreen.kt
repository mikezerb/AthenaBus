package com.example.athenabus.presentation.onboarding

import android.content.res.Configuration
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.example.athenabus.R
import com.example.athenabus.data.local.PageData.pages
import com.example.athenabus.presentation.common.MaterialElevatedButton
import com.example.athenabus.presentation.common.MaterialTextButton
import com.example.athenabus.presentation.onboarding.components.OnBoardingPage
import com.example.athenabus.presentation.onboarding.components.PageIndicator
import com.example.athenabus.ui.theme.Dimens.PageIndicatorWidth
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class, ExperimentalFoundationApi::class)
@Composable
fun OnBoardingScreen(
    event: (OnBoardingEvent) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Center
    ) {
        val scope = rememberCoroutineScope()

        val pagerState = rememberPagerState(initialPage = 0) {
            pages.size
        }
        val context = LocalContext.current
        val buttonState = remember {
            derivedStateOf {
                when (pagerState.currentPage) {
                    0 -> listOf("", context.getString(R.string.next))
                    1 -> listOf("", context.getString(R.string.next))
                    2 -> listOf("", context.getString(R.string.get_started))
                    else -> listOf("", "")
                }
            }
        }
        Box(
            modifier = Modifier
                .fillMaxHeight(0.8f)
                .fillMaxWidth()
        ) {
            Column(horizontalAlignment = Alignment.End) {
                IconButton(
                    onClick = {
                        scope.launch {
                            event(OnBoardingEvent.SaveAppEntry)
                        }
                    }) {
                    Icon(imageVector = Icons.Default.Close, contentDescription = null)
                }
                HorizontalPager(state = pagerState) { index ->
                    OnBoardingPage(page = pages[index])
                }
            }
        }

        Row(
            Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            if (pagerState.currentPage != 2)
                PageIndicator(
                    modifier = Modifier.width(PageIndicatorWidth),
                    pageNums = pages.size,
                    selectedPage = pagerState.currentPage
                )

            if (buttonState.value[0].isNotEmpty()) {
                MaterialTextButton(
                    text = buttonState.value[0],
                    onClick = {
                        scope.launch {
                            pagerState.animateScrollToPage(page = pagerState.currentPage - 1)
                        }
                    }
                )
            }

            MaterialElevatedButton(
                text = buttonState.value[1],
                onClick = {
                    scope.launch {
                        if (pagerState.currentPage == 2) {
                            event(OnBoardingEvent.SaveAppEntry)
                        } else {
                            pagerState.animateScrollToPage(page = pagerState.currentPage + 1)
                        }

                    }
                }
            )
        }
    }
}


@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun OnBoardingPreview() {
    OnBoardingScreen(
        event = { }
    )
}
