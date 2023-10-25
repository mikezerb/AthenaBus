package com.example.athenabus.presentation.line_details.components.tabs

import DropdownMenuSelection
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.athenabus.R
import com.example.athenabus.domain.model.Line
import com.example.athenabus.presentation.line_details.RouteScheduleViewModel

@Composable
fun ScheduleScreen(
    modifier: Modifier = Modifier,
    line: Line,
    lines: List<Line>,
    viewModel: RouteScheduleViewModel = hiltViewModel()
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedLine by remember {
        mutableStateOf("")
    }
    var selectedLineCode by remember {
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

        DropdownMenuSelection(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            itemList = lines.map { it.LineDescr },
            initialText = stringResource(id = R.string.choose_direction),
            onDismiss = { expanded = false },
            onClick = { line, i ->
                selectedLine = line
                selectedLineCode = lines[i].LineCode
                expanded = false
                viewModel.getDailySchedules(selectedLineCode)
            },
            selectedOption = selectedLine,
            expanded = expanded,
            onExpanded = { expanded = it },
        )

    }
}
