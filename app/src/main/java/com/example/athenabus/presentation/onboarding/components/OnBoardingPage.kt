package com.example.athenabus.presentation.onboarding.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.athenabus.data.local.PageData
import com.example.athenabus.presentation.onboarding.Page
import com.example.athenabus.ui.theme.Dimens.MediumPadding1
import com.example.athenabus.ui.theme.Dimens.SmallPadding1

@Composable
fun OnBoardingPage(
    modifier: Modifier = Modifier,
    page: Page
) {
    val context = LocalContext.current
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = modifier,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(fraction = 0.6f),
                painter = painterResource(id = page.image),
                contentDescription = null,
                contentScale = ContentScale.Fit
            )
            Spacer(modifier = Modifier.width(MediumPadding1))
            Text(
                text = context.getString(page.titleResId),
                modifier = Modifier
                    .padding(horizontal = SmallPadding1)
                    .padding(bottom = 18.dp, top = 12.dp),
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Center
            )
            Text(
                text = context.getString(page.descriptionResId),
                modifier = Modifier.padding(horizontal = MediumPadding1),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onBackground,
            )
        }
    }

}

@Preview
@Composable
fun OnBoardingPreview() {
    OnBoardingPage(
        page = PageData.pages[2]
    )
}
