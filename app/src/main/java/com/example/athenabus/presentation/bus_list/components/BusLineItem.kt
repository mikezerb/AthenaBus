package com.example.athenabus.presentation.bus_list.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.athenabus.domain.model.Line

@Composable
fun BusLineItem(
    busLine: Line,
    onItemClick: (Line) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
        ),
        border = BorderStroke(1.dp, Color.Black),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        )
    ) {
        Column(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
                .clickable { onItemClick(busLine) }
        ) {
            // Display Line Code
            Text(
                text = busLine.LineID,
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier.padding(bottom = 8.dp, start = 4.dp)
            )
            // Display Line Description
            Text(
                text = busLine.LineDescr,
                fontSize = 14.sp,
                modifier = Modifier.padding(bottom = 4.dp, start = 4.dp)
            )
        }

    }

}
