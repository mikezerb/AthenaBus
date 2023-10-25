package com.example.athenabus.presentation.line_details.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.athenabus.domain.model.ComeUI
import com.example.athenabus.domain.model.DailySchedule
import com.example.athenabus.domain.model.GoUI
import com.example.athenabus.ui.utils.DayNightPreview

@Composable
fun TimetableItem(
    modifier: Modifier = Modifier,
    dailySchedule: DailySchedule,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.Top
    ) {
        LazyColumn {
            items(dailySchedule.go) {
                Text(text = it.time, modifier = Modifier.padding(4.dp))
            }
        }
        LazyColumn {
            items(dailySchedule.come) {
                Text(text = it.time, modifier = Modifier.padding(4.dp))
            }
        }
    }
}

@DayNightPreview
@Composable
private fun PreviewTimetableItem() {
    val comeList = listOf<ComeUI>(
        ComeUI(
            "10:00"
        ),
        ComeUI(
            "12:00"
        ),
        ComeUI(
            "14:00"
        ),
        ComeUI(
            "16:00"
        ),
        ComeUI(
            "18:00"
        ),
        ComeUI(
            "20:00"
        ),
    )
    val goList = listOf<GoUI>(
        GoUI(
            "9:00"
        ),
        GoUI(
            "12:00"
        ),
        GoUI(
            "14:00"
        ),
        GoUI(
            "16:00"
        ),
        GoUI(
            "18:00"
        ),
        GoUI(
            "20:00"
        ),
    )

    val daily = DailySchedule(come = comeList, go = goList)
    TimetableItem(dailySchedule = daily)
}