package com.example.athenabus.presentation.line_details.components.tabs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.athenabus.R
import com.example.athenabus.domain.model.Line
import com.example.athenabus.domain.model.Route
import com.example.athenabus.presentation.line_details.components.AutoCompleteTextField

@Composable
fun StopsScreen(
    modifier: Modifier = Modifier,
    line: Line,
    routes: List<Route>
) {

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(4.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        val options: MutableList<String> = mutableListOf()
        if (routes.isNotEmpty()) {
            routes.forEach { route ->
                options += route.RouteDescr
            }
        }
        AutoCompleteTextField(
            initialText = stringResource(R.string.choose_direction),
            itemList = options
        )
        Text(text = "Stops for Line: " + line.LineID + " " + line.LineDescr)
        Text(text = "Routes for Line: " + line.LineID + " : " + routes.size)
    }

}
