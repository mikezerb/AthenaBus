package com.example.athenabus.presentation.bus_list.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterAltOff
import androidx.compose.material.icons.outlined.FilterAlt
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun ShowFilterButton(
    showFilterView: Boolean,
    onClick: () -> Unit
) {
    val filterIcon =
        if (showFilterView) Icons.Default.FilterAltOff else Icons.Outlined.FilterAlt

    IconButton(
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