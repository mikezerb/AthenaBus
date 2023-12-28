package com.example.athenabus.presentation.bus_list.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.example.athenabus.domain.model.Line
import com.example.athenabus.sample.SampleLineProvider

@Composable
fun BusLineListItem(
    modifier: Modifier = Modifier,
    busLine: Line,
    onItemClick: (Line) -> Unit,
    onToggleFavorite: (Line) -> Unit,
    showFavouriteIcon: Boolean = false
) {
    val color = MaterialTheme.colorScheme.surfaceVariant
    val interactionSource = remember { MutableInteractionSource() }
    val context = LocalContext.current
    ListItem(
        modifier = modifier
            .clickable(
                onClick = { onItemClick(busLine) },
                interactionSource = interactionSource,
                indication = rememberRipple(),
            )
            .fillMaxWidth(),
        leadingContent = {
            BusLineIDType(
                lineId = busLine.LineID,
                category = busLine.Category,
                context = context
            )
        },
        headlineContent = { // Display Line Description
            Text(
                text = busLine.LineDescr,
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

@Preview(name = "BusLineListItem")
@Composable
private fun PreviewBusLineListItem(@PreviewParameter(SampleLineProvider::class) line: Line) {
    BusLineListItem(
        modifier = Modifier.fillMaxWidth(),
        busLine = line,
        onItemClick = { },
        onToggleFavorite = { },
    )
}
