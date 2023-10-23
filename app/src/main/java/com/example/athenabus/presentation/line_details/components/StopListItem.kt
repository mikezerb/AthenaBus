package com.example.athenabus.presentation.line_details.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.StopCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.athenabus.domain.model.Stop

@Composable
fun StopListItem(
    modifier: Modifier = Modifier,
    stop: Stop,
    onItemClick: (Stop) -> Unit,
) {
    ListItem(
        modifier = modifier
            .clickable { onItemClick(stop) }
            .fillMaxWidth()
            .wrapContentHeight(),
        leadingContent = { // Display Line Code
            Icon(imageVector = Icons.Outlined.StopCircle, contentDescription = null)
        },
        headlineContent = { // Display Line Description
            Text(
                text = stop.StopDescr,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Start,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
    )
}

@Preview
@Composable
private fun Preview() {
    StopListItem(
        stop = Stop(
            StopCode = "", StopDescrEng = "", StopDescr = "FILOLAOY",
            StopLng = "", StopLat = "", StopStreet = null, StopID = "123", distance = ""
        ),
        onItemClick = { }
    )
}

/*
StopCode: String,
    val StopDescr: String,
    val StopDescrEng: String?,
    val StopStreet: String?,
    val StopLat: String,
    val StopLng: String,
    val StopID: String,
    val distance: String
 */