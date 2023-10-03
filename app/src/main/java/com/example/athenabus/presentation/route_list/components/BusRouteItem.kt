package com.example.athenabus.presentation.route_list.components

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.athenabus.domain.model.Route
import com.example.athenabus.ui.theme.AthenaBusTheme

@Composable
fun BusRouteItem(
    busRoute: Route,
    onItemClick: (Route) -> Unit,
) {
    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onItemClick(busRoute) }, horizontalAlignment = CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = busRoute.RouteDescr,
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier
                    .padding(8.dp)
                    .align(CenterHorizontally),
            )
        }
    }


}

@Preview(showBackground = true)
@Preview(uiMode = UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun testRouteItem() {
    AthenaBusTheme {
        BusRouteItem(
            busRoute = Route(
                RouteCode = "919",
                RouteDescr = "ATHINA - THESALIA",
                RouteDescrEng = "agsag",
                LineCode = "12412",
                RouteType = "1",
                RouteDistance = "2521"
            ), onItemClick = {})
    }
}