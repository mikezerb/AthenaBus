package com.example.athenabus.presentation.line_details.tabs

import DropdownMenuSelection
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.athenabus.R
import com.example.athenabus.domain.model.Line
import com.example.athenabus.presentation.line_details.LineDetailsViewModel
import com.example.athenabus.presentation.line_details.components.TimetableItem

@Composable
fun ScheduleScreen(
    modifier: Modifier = Modifier,
    line: Line,
    viewModel: LineDetailsViewModel = hiltViewModel(),
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedLine by remember {
        mutableStateOf("")
    }
    var selectedLineCode by remember {
        mutableStateOf("")
    }

    var options: MutableList<String> = mutableListOf()
    val state = viewModel.scheduleState.value

    LaunchedEffect(key1 = true, key2 = line) {
        viewModel.getAvailableLines(line.LineID)
    }

    val context = LocalContext.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(4.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text(text = "Schedules for Line: " + line.LineID + " " + line.LineDescr)
        Text(text = "Lines available: " + state.availableLines.size)

        DropdownMenuSelection(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            itemList = state.availableLines.map { it.LineDescr },
            initialText = stringResource(id = R.string.choose_direction),
            onDismiss = { expanded = false },
            onClick = { line, i ->
                selectedLine = line
                selectedLineCode = state.availableLines[i].LineCode
                expanded = !expanded
                Toast.makeText(
                    context,
                    "Selected $selectedLine with $selectedLineCode",
                    Toast.LENGTH_SHORT
                ).show()
                viewModel.getDailySchedules(selectedLineCode)
            },
            selectedOption = selectedLine,
            expanded = expanded,
            onExpanded = { expanded = it },
        )

        Text(text = "Found ${state.schedules?.go?.size} go and ${state.schedules?.come?.size} come!")
        if (state.schedules != null) {
            TimetableItem(dailySchedule = state.schedules)
        }
        if (state.isLoading) {
            CircularProgressIndicator()
        }
        if (state.error.isNotBlank()) {
            Text(text = state.error, color = MaterialTheme.colorScheme.error)
        }
    }
}
