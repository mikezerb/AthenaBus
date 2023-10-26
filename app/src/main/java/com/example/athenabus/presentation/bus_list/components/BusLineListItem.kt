package com.example.athenabus.presentation.bus_list.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
fun BusLineListItem(
    modifier: Modifier = Modifier,
    busLine: Line,
    onItemClick: (Line) -> Unit,
    onToggleFavorite: (Line) -> Unit,
    showFavouriteIcon: Boolean = false
) {
    val color = MaterialTheme.colorScheme.surfaceVariant

    ListItem(
        modifier = modifier
            .clickable { onItemClick(busLine) }
            .fillMaxWidth(),
        leadingContent = { // Display Line ID
            Text(
                modifier = Modifier
                    .drawBehind {
                        drawRoundRect(
                            color = color,
                            cornerRadius = CornerRadius(16.dp.toPx())
                        )
                    }
                    .padding(horizontal = 8.dp)
                    .defaultMinSize(minWidth = 52.dp),
                text = busLine.LineID,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                style = MaterialTheme.typography.headlineMedium
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
