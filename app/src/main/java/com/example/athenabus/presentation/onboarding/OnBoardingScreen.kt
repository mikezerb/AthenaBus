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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.athenabus.data.local.PageData.pages
import com.example.athenabus.presentation.Dimens.MediumPadding1
import com.example.athenabus.presentation.Dimens.PageIndicatorWidth
import com.example.athenabus.presentation.common.MaterialElevatedButton
import com.example.athenabus.presentation.common.MaterialTextButton
import com.example.athenabus.presentation.onboarding.components.OnBoardingPage
import com.example.athenabus.presentation.onboarding.components.PageIndicator
import com.example.athenabus.ui.theme.AthenaBusTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class, ExperimentalFoundationApi::class)
@Composable
fun OnBoardingScreen(
    event: (OnBoardingEvent) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        val pagerState = rememberPagerState(initialPage = 0) {
            pages.size
        }

        val buttonState = remember {
            derivedStateOf {
                when (pagerState.currentPage) {
                    0 -> listOf("", "Next")
                    1 -> listOf("Back", "Next")
                    2 -> listOf("Back", "Get Started")
                    else -> listOf("", "")
                }
            }
        }
        Box(
            modifier = Modifier
                .fillMaxHeight(0.8f)
                .fillMaxWidth()
        ) {
            HorizontalPager(state = pagerState) { index ->
                OnBoardingPage(page = pages[index])
            }
        }
        PageIndicator(
            modifier = Modifier.width(PageIndicatorWidth),
            pageNums = pages.size,
            selectedPage = pagerState.currentPage
        )
        Row(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = MediumPadding1),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            val scope = rememberCoroutineScope()

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
fun OnBoardingPview() {
    AthenaBusTheme {
        OnBoardingScreen(
            event = { }
        )
    }

}
