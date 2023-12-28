package com.example.athenabus.presentation.favorites.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ElevatedAssistChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun RoutesChipItem(
    modifier: Modifier = Modifier,
    routes: List<String> = emptyList()
) {
    LazyRow{
        items(routes.distinct()) { route ->
            ElevatedAssistChip(
                modifier = Modifier.padding(4.dp),
                onClick = { /*TODO*/ },
                label = { Text(text = route) }
            )

        }
    }
}

@Preview(name = "RoutesChipItem")
@Composable
private fun PreviewRoutesChipItem() {

    var routes = listOf("608", "608", "550", "224")
    Column(Modifier.fillMaxSize()) {
        RoutesChipItem(routes = routes)
    }
}