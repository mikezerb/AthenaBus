package com.example.athenabus.presentation.line_details.components.tabs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.athenabus.domain.model.Line

@Composable
fun ScheduleScreen(
    modifier: Modifier = Modifier,
    line: Line,
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedRoute by remember {
        mutableStateOf("")
    }
    var selectedRouteCode by remember {
        mutableStateOf("")
    }
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(4.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text(text = "Schedules for Line: " + line.LineID + " " + line.LineDescr)
    }
}
