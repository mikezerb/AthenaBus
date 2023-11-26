package com.example.athenabus.presentation.bus_list.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.athenabus.domain.model.Line
import com.example.athenabus.presentation.common.FavoriteButton

@Composable
fun GridBusLineItem(
    busLine: Line,
    onItemClick: (Line) -> Unit,
    onToggleFavorite: (Line) -> Unit,
    showFavorite: Boolean = false
) {
    OutlinedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
            .clickable { onItemClick(busLine) },
        border = BorderStroke(
            width = 1.dp,
            color = MaterialTheme.colorScheme.surfaceContainer
        )
    ) {
        Box(modifier = Modifier) {
            Column(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxSize()
            ) {
                Surface(
                    modifier = Modifier.defaultMinSize(minWidth = 65.dp),
                    tonalElevation = 4.dp,
                    shape = RoundedCornerShape(16.dp),
                    border = BorderStroke(
                        width = .5.dp,
                        color = MaterialTheme.colorScheme.surfaceContainer
                    )
                ) {
                    Text(
                        modifier = Modifier.padding(horizontal = 8.dp),
                        text = busLine.LineID,
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.headlineMedium
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = busLine.LineDescr,
                    style = MaterialTheme.typography.bodySmall,
                    textAlign = TextAlign.Start,
                    modifier = Modifier.padding(bottom = 4.dp, start = 4.dp)
                )
            }
            if (showFavorite) {
                FavoriteButton(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(8.dp),
                    onClick = { onToggleFavorite(busLine) },
                    isFavorite = busLine.isFavorite
                )
            }
        }
    }
}

@Preview(name = "GridBusLineItem")
@Composable
private fun PreviewGridBusLineItem() {

}