package com.example.athenabus.presentation.onboarding.components

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.athenabus.data.local.PageData
import com.example.athenabus.presentation.Dimens.MediumPadding1
import com.example.athenabus.presentation.onboarding.Page
import com.example.athenabus.presentation.theme.AthenaBusTheme

@Composable
fun OnBoardingPage(
    modifier: Modifier = Modifier,
    page: Page
) {
    Column(
        modifier = modifier
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
            text = page.title,
            modifier = Modifier.padding(horizontal = MediumPadding1),
            style = MaterialTheme.typography.headlineLarge
        )
        Text(
            text = page.description,
            modifier = Modifier.padding(horizontal = MediumPadding1),
            style = MaterialTheme.typography.bodyMedium
        )
    }
}
@Preview(showBackground = true)
@Preview(uiMode = UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun OnBoardingPreview() {
    AthenaBusTheme {
        OnBoardingPage(
            page = PageData.pages[0]
        )
    }

}