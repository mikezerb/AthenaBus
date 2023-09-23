package com.example.athenabus.presentation.search_screen.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.athenabus.domain.model.Line


@Composable
fun LineItem(
    line: Line,
    modifier: Modifier = Modifier
) {

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f))
        {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = line.LineID)

            }
        }

    }

}