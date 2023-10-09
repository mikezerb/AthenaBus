package com.example.athenabus.presentation.closest_stops.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.example.athenabus.R
import com.example.athenabus.domain.model.Arrival
import com.example.athenabus.domain.model.Stop
import com.example.athenabus.sample.SampleStopProvider

@Composable
fun ClosestStopItem(
    stop: Stop,
    arrivals: List<Arrival> = emptyList(),
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    var arrival_times = ""

    if(arrivals.isNotEmpty()){
        arrival_times = arrivals.forEach {
            it.route_code + " " + it.btime2 + " λεπτά"
        }.toString()
    }
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() },
    ) {
        Row(
            modifier = Modifier.padding(start = 8.dp),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(imageVector = Icons.Default.ArrowRight, contentDescription = null)
            Column(
                modifier = Modifier.padding(12.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start
            ) {
                Text(text = stop.StopDescr, style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "${stop.StopStreet ?: ""}(${stop.StopCode})",
                    style = MaterialTheme.typography.labelSmall
                )
                Text(
                    text = "Distance: %.2f%s".format(
                        stop.distance.toDouble() * 100, stringResource(
                            id = R.string.km
                        )
                    ),
                    style = MaterialTheme.typography.labelSmall
                )
            }
        }
    }
}

@Preview(name = "ClosestStopItem")
@Composable
private fun PreviewClosestStopItem(@PreviewParameter(SampleStopProvider::class) stop: Stop) {
    ClosestStopItem(stop = stop, onClick = { })
}