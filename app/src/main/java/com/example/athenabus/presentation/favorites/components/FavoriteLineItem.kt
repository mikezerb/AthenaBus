package com.example.athenabus.presentation.favorites.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.example.athenabus.domain.model.Line
import com.example.athenabus.sample.SampleLineProvider

@Composable
fun FavoriteLineItem(
    modifier: Modifier = Modifier,
    busLine: Line,
    onItemClick: () -> Unit,
    isEnglish: Boolean = false
) {
    val interactionSource = remember { MutableInteractionSource() }
    val background = MaterialTheme.colorScheme.primary
    val textColor = MaterialTheme.colorScheme.onPrimary
    ListItem(
        modifier = modifier
            .clickable(
                onClick = { onItemClick() },
                interactionSource = interactionSource,
                indication = rememberRipple(),
            )
            .fillMaxWidth(),
        leadingContent = {
            Text(
                modifier = Modifier
                    .drawBehind {
                        drawRoundRect(
                            color = background,
                            cornerRadius = CornerRadius(12.dp.toPx())
                        )
                    }
                    .defaultMinSize(minWidth = 52.dp)
                    .padding(horizontal = 6.dp),
                text = busLine.LineID,
                style = MaterialTheme.typography.headlineMedium,
                textAlign = TextAlign.Center,
                color = textColor
            )

        },
        headlineContent = { // Display Line Description
            Text(
                text = if (isEnglish) busLine.LineDescrEng else busLine.LineDescr,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Start,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        },
        overlineContent = {
//            LineCategoryChip(
//                modifier = Modifier.padding(bottom = 4.dp),
//                category = busLine.Category,
//                context = context,
//                showTitle = true
//            )
        }
    )

}

@Preview(name = "FavoriteLineItem")
@Composable
private fun PreviewFavoriteLineItem(@PreviewParameter(SampleLineProvider::class) busLine: Line) {
    FavoriteLineItem(
        modifier = Modifier.fillMaxWidth(),
        busLine = busLine,
        onItemClick = { /*TODO*/ })
}