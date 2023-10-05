package com.example.athenabus.presentation.bus_list.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
            .padding(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        ),
    ) {
        Box(modifier = Modifier.clickable { onItemClick(busLine) }) {
            Column(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxSize()
            ) {
                // Display Line Code
                Row(
                    modifier = Modifier
                        .background(
                            color = MaterialTheme.colorScheme.secondaryContainer,
                            shape = RoundedCornerShape(12.dp)
                        )
                        .clip(shape = CircleShape)
                        .wrapContentSize(Alignment.Center)
                        .padding(horizontal = 6.dp),
                ) {
                    Text(
                        text = busLine.LineID,
                        style = MaterialTheme.typography.displaySmall,
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                // Display Line Description
                Text(
                    text = busLine.LineDescr,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(bottom = 4.dp, start = 4.dp)
                )
            }
            if(showFavorite) {
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