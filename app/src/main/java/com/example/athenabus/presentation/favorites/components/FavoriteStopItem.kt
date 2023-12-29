package com.example.athenabus.presentation.favorites.components

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.TransferWithinAStation
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.example.athenabus.R
import com.example.athenabus.domain.model.Stop

@Composable
fun FavoriteStopItem(
    modifier: Modifier = Modifier,
    stop: Stop,
    routes: List<String>? = emptyList(),
    onRouteClick: (String) -> Unit,
    onDelete: (String) -> Unit
) {
    var isContextMenuVisible by rememberSaveable {
        mutableStateOf(false)
    }
    var pressOffset by remember {
        mutableStateOf(DpOffset.Zero)
    }
    var itemHeight by remember {
        mutableStateOf(0.dp)
    }
    ListItem(
        modifier = modifier
            .fillMaxWidth()
            .onSizeChanged {
                itemHeight = it.height.dp
            }
            .pointerInput(true) {
                detectTapGestures(
                    onLongPress = {
                        isContextMenuVisible = true
                        pressOffset = DpOffset(it.x.dp, it.y.toDp())
                    }
                )
            },
        leadingContent = {
            Icon(
                imageVector = Icons.Default.TransferWithinAStation,
                contentDescription = null
            )
        },
        headlineContent = { Text(text = stop.StopDescr) },
        supportingContent = {
            routes?.let {
                RoutesChipItem(
                    routes = it,
                    onRouteClick = { lineId -> onRouteClick(lineId) })
            }
        }
    )
    DropdownMenu(
        expanded = isContextMenuVisible,
        onDismissRequest = { isContextMenuVisible = false },
        offset = pressOffset.copy(
            y = pressOffset.y - itemHeight
        )
    ) {
        DropdownMenuItem(
            leadingIcon = { Icon(imageVector = Icons.Default.Delete, contentDescription = null) },
            text = {
                Text(text = stringResource(R.string.remove_stop, stop.StopDescr))
            },
            onClick = {
                onDelete(stop.StopCode)
                isContextMenuVisible = false
            })
    }
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
        onRouteClick = { },
        onDelete = { }
    )
}