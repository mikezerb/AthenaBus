package com.example.athenabus.presentation.bus_list.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.athenabus.domain.model.Line
import com.example.athenabus.presentation.common.FavoriteButton
import com.example.athenabus.presentation.theme.AthenaBusTheme

@Composable
fun BusLineItem(
    busLine: Line,
    onItemClick: (Line) -> Unit,
    onToggleFavorite: (Line) -> Unit
) {
    // Define a mutable state to track the star state
    var isStarred by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
        ),
        border = BorderStroke(1.dp, Color.Black),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        )
    ) {
        Box(modifier = Modifier.clickable { onItemClick(busLine) }) {
            Column(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
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
                Spacer(modifier = Modifier.height(6.dp))
                // Display Line Description
                Text(
                    text = busLine.LineDescr,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(bottom = 4.dp, start = 4.dp)
                )
            }

            FavoriteButton(modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(8.dp),
                onClick = { onToggleFavorite(busLine) }
            )
        }
    }
}

@Preview
@Composable
fun gete() {
    AthenaBusTheme {
        BusLineItem(
            Line("123", "421", "AGIA PARASKEVI - NEA IONIA", "assaf"),
            onItemClick = { },
            onToggleFavorite = { })
    }
}
