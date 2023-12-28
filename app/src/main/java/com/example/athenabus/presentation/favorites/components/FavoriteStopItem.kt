package com.example.athenabus.presentation.favorites.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.TransferWithinAStation
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.athenabus.domain.model.Stop

@Composable
fun FavoriteStopItem(
    modifier: Modifier = Modifier,
    stop: Stop,
    routes: List<String>? = emptyList(),
) {

    ListItem(
        modifier = modifier.fillMaxWidth(),
        leadingContent = {
            Icon(
                imageVector = Icons.Default.TransferWithinAStation,
                contentDescription = null
            )
        },
        headlineContent = { Text(text = stop.StopDescr) },
        supportingContent = {
            routes?.let { RoutesChipItem(routes = it) }
        }

    )
}

@Preview
@Composable
private fun PreviewFavoriteStopItem() {
    FavoriteStopItem(
        stop = Stop(
            StopCode = "123214",
            StopID = "dasfas",
            StopDescr = "AG. PARASKEVI",
            StopDescrEng = null,
            StopLat = "0",
            StopLng = "0",
            distance = "",
            StopStreet = null
        ),
    )
}