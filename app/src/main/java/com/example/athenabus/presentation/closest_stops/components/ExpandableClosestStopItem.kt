package com.example.athenabus.presentation.closest_stops.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.athenabus.R
import com.example.athenabus.domain.model.Arrival
import com.example.athenabus.domain.model.Route
import com.example.athenabus.domain.model.Stop

@Composable
fun ExpandableClosestStopItem(
    stop: Stop,
    routes: List<Route> = emptyList(),
    arrivals: List<Arrival> = emptyList(),
    modifier: Modifier = Modifier,
    expanded: Boolean,
    onClick: () -> Unit = { },
    onExpandClick: (id: Int) -> Unit = { }
) {

    val rotationState by animateFloatAsState(
        targetValue = if (expanded) 180f else 0f, label = ""
    )
    var available_routes = ""

    if (routes.isNotEmpty()) {
        routes.filter { it.hidden != "1" }.forEachIndexed { index, route ->
            if (arrivals.isNotEmpty()) {
                arrivals.filter { it.route_code == route.RouteCode }
            }
            available_routes += "${route.LineID}: ${route.RouteDescr}"
            available_routes += if (index < routes.size - 1) {
                "\n"
            } else {
                " "
            }
        }
    }

    var arrivalsBuses = ""
    if (arrivals.isNotEmpty()) {
        arrivals.groupBy { it.route_code }.forEach {
            var times = ""
            it.value.forEachIndexed { index, arrival ->
                times += "${arrival.btime2} ${stringResource(id = R.string.min)}"
                times += if (index < it.value.size - 1) {
                    " & "
                } else
                    "\n"
            }
            arrivalsBuses += "${it.value.first().LineID}: " + times
        }
    }

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize(
                animationSpec = tween(durationMillis = 300, easing = LinearOutSlowInEasing)
            ),
        onClick = onClick
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(

                ) {
                    Text(text = stop.StopDescr, style = MaterialTheme.typography.titleSmall)
                    Text(
                        text = "${stop.StopStreet ?: ""} (${stop.StopCode})",
                        style = MaterialTheme.typography.labelMedium
                    )
                    Text(text = runCatching {
                        val distance = stop.distance.toDoubleOrNull() ?: 0.0
                        "%s: %.2f%s".format(
                            stringResource(id = R.string.distance),
                            distance * 100, stringResource(id = R.string.km)
                        )
                    }.getOrElse {
                        "Distance: N/A"
                    },
                        style = MaterialTheme.typography.labelMedium
                    )
                }
                IconButton(
                    modifier = Modifier.rotate(rotationState),
                    onClick = { onExpandClick(stop.StopCode.toInt()) }) {
                    Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = null)
                }
            }
            AnimatedVisibility(
                visible = expanded,
                enter = expandVertically(
                    expandFrom = Alignment.Top,
                    animationSpec = tween(durationMillis = 200)
                ) + fadeIn(
                    initialAlpha = 0.3f, animationSpec = tween(200)
                ),
                exit = shrinkVertically(
                    shrinkTowards = Alignment.Top,
                    animationSpec = tween(durationMillis = 200)
                )
                        + fadeOut(
                    animationSpec = tween(200)
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Surface(
                    tonalElevation = 4.dp,
                ) {
                    Column(
                        modifier = Modifier
                            .padding(start = 12.dp)
                    ) {
                        Text(
                            text = routes.size.toString() + " ${stringResource(id = R.string.available_routes)}" + "\n" +
                                    available_routes + "\n" +
                                    arrivals.size.toString() + " ${stringResource(id = R.string.arriving_buses)}" + "\n" +
                                    arrivalsBuses,
                            style = MaterialTheme.typography.labelMedium
                        )

                    }
                }
            }

        }
    }

}

@Preview
@Composable
fun ExpandableClosestStopItemPreview() {
    val stop = Stop(
        StopCode = "",
        StopDescr = "ΚΟΤΟΠΟΥΛΗ",
        StopLng = "",
        StopLat = "",
        StopID = "",
        StopStreet = "AKADIMIA",
        StopDescrEng = "",
        distance = "0.001443313361679744"
    )
    /*
    val RouteCode: String,
val LineCode: String,
val RouteDescr: String,
val RouteDescrEng: String,
val RouteDistance: String,
val RouteType: String,
val LineID: String,
val hidden: String,
val LineDescr: String,
val LineID: String,
val MasterLineCode: String
     */
    val availableRoutes = listOf(
        Route(
            RouteCode = "",
            LineCode = "",
            RouteDescr = "NEKR. ZOGRAFOU - AKADIMIA - GALATSI [TO AKADIMIA AFTER MIDNIGHT]",
            RouteDescrEng = "",
            RouteType = "",
            LineID = "608",
            hidden = "",
            LineDescr = "NEKR. ZOGRAFOU - AKADIMIA - GALATSI",
            MasterLineCode = "",
            RouteDistance = "",
            LineDescrEng = "",
        ),
        Route(
            RouteCode = "",
            LineCode = "",
            RouteDescr = "GALATSI - AKADIMIA - NEKR. ZOGRAFOY (SATURDAY AFTER MIDNIGHT TRIPS",
            RouteDescrEng = "",
            RouteType = "",
            LineID = "235",
            hidden = "",
            LineDescr = "NEKR. ZOGRAFOU - AKADIMIA - GALATSI",
            MasterLineCode = "",
            RouteDistance = "",
            LineDescrEng = "",
        ),

        )
    ExpandableClosestStopItem(stop = stop, routes = availableRoutes, expanded = true)
}