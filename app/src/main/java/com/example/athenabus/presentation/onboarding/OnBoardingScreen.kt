package com.example.athenabus.presentation.onboarding

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.athenabus.data.local.PageData.pages
import com.example.athenabus.presentation.Dimens.MediumPadding1
import com.example.athenabus.presentation.Dimens.PageIndicatorWidth
import com.example.athenabus.presentation.common.MaterialElevatedButton
import com.example.athenabus.presentation.common.MaterialTextButton
import com.example.athenabus.presentation.onboarding.components.OnBoardingPage
import com.example.athenabus.presentation.onboarding.components.PageIndicator
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class, ExperimentalFoundationApi::class)
@Composable
fun OnBoardingScreen(
    event: (OnBoardingEvent) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        val pagerState = rememberPagerState(initialPage = 0){
            pages.size
        }

        val buttonState = remember {
            derivedStateOf {
                when (pagerState.currentPage){
                    0 -> listOf("", "Next")
                    1 -> listOf("Back", "Next")
                    2 -> listOf("Back", "Get Started")
                    else -> listOf("", "")
                }
            }
        }

        HorizontalPager(state = pagerState) {
            index ->
            OnBoardingPage(page = pages[index])
        }
        Spacer(modifier = Modifier.weight(1f))
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = MediumPadding1)
                .navigationBarsPadding(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            PageIndicator(
                modifier = Modifier.width(PageIndicatorWidth),
                pageNums = pages.size,
                selectedPage = pagerState.currentPage
            )

            Row (
                verticalAlignment = Alignment.CenterVertically
            ){

                val scope = rememberCoroutineScope()

                if(buttonState.value[0].isNotEmpty()){
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
                            if(pagerState.currentPage == 2){
                                event(OnBoardingEvent.SaveAppEntry)
                            } else{
                                pagerState.animateScrollToPage(page = pagerState.currentPage + 1)
                            }

                        }
                    }
                    )
            }
        }
        Spacer(modifier = Modifier.weight(0.2f))
    }
}
