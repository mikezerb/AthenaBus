package com.example.athenabus.presentation.line_details.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.dp
import com.example.athenabus.domain.model.Stop
import com.example.athenabus.ui.utils.DayNightPreview

@Composable
fun BusStopItems(
    modifier: Modifier = Modifier,
    items: List<Stop>,
    onStopClick: (Stop) -> Unit
) {
    val color = MaterialTheme.colorScheme.primary
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(4.dp),
        contentPadding = PaddingValues(0.dp)
    ) {
        itemsIndexed(items) { index, stop ->
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .clickable {
                        onStopClick(stop)
                    },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start,
            ) {
                Canvas(modifier = Modifier.size(36.dp)) {
                    when (index) {
                        0 -> {
                            drawLine(
                                color = color,
                                start = Offset(x = size.width / 2f, y = size.height),
                                end = Offset(x = size.width / 2f, y = size.height / 2f),
                                strokeWidth = 10f
                            )
                        }

                        items.lastIndex -> {
                            drawLine(
                                color = color,
                                start = Offset(x = size.width / 2f, y = 0f),
                                end = Offset(x = size.width / 2f, y = size.height / 2f),
                                strokeWidth = 10f
                            )
                        }

                        else -> {
                            drawLine(
                                color = color,
                                start = Offset(x = size.width / 2, y = 0f),
                                end = Offset(x = size.width / 2, y = size.height),
                                strokeWidth = 10f
                            )
                        }
                    }
                    drawCircle(
                        color = color,
                        radius = size.width / 4f
                    )
                }
                // Add some horizontal space between the circle and the text
                Spacer(modifier = Modifier.width(16.dp))
                // Display the text
                Text(
                    modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp),
                    text = stop.StopDescr,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

@DayNightPreview
@Composable
private fun PreviewBusStopItem() {
    val testStops = listOf(
        Stop(
            StopCode = "",
            StopDescr = "FILOLAOY",
            StopLat = "",
            StopLng = "",
            StopID = "",
            distance = "",
            StopStreet = "",
            StopDescrEng = ""
        ),
        Stop(
            StopCode = "",
            StopDescr = "NOTARA",
            StopLat = "",
            StopLng = "",
            StopID = "",
            distance = "",
            StopStreet = "",
            StopDescrEng = ""
        ),
        Stop(
            StopCode = "",
            StopDescr = "FILOLAOY",
            StopLat = "",
            StopLng = "",
            StopID = "",
            distance = "",
            StopStreet = "",
            StopDescrEng = ""
        ),
        Stop(
            StopCode = "",
            StopDescr = "ZWGRAFOY 11",
            StopLat = "",
            StopLng = "",
            StopID = "",
            distance = "",
            StopStreet = "",
            StopDescrEng = ""
        ),
        Stop(
            StopCode = "",
            StopDescr = "DOKIMI",
            StopLat = "",
            StopLng = "",
            StopID = "",
            distance = "",
            StopStreet = "",
            StopDescrEng = ""
        )


    )
    Stop(
        StopCode = "",
        StopDescr = "FILOLAOY",
        StopLat = "",
        StopLng = "",
        StopID = "",
        distance = "",
        StopStreet = "",
        StopDescrEng = ""
    )
    Surface {
        BusStopItems(items = testStops, onStopClick = { })
    }
}