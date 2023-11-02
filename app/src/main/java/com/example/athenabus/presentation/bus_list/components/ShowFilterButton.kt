package com.example.athenabus.presentation.bus_list.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterAlt
import androidx.compose.material.icons.filled.FilterAltOff
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.FilterListOff
import androidx.compose.material.icons.outlined.FilterAlt
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun ShowFilterButton(
    modifier: Modifier = Modifier,
    showFilterView: Boolean,
    onClick: () -> Unit
) {
    val filterIcon =
        if (showFilterView) Icons.Default.FilterListOff else Icons.Default.FilterList

    IconButton(
        modifier = modifier,
        onClick = onClick,
    ) {
        Icon(
            imageVector = filterIcon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.surfaceTint
        )
    }
}

@Preview
@Composable
private fun PreviewShowFilterButton() {
    var enableFilter by remember {
        mutableStateOf(false)
    }
    ShowFilterButton(showFilterView = enableFilter, onClick = { enableFilter = !enableFilter })
}