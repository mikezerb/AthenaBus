package com.example.athenabus.presentation.bus_list.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.GridView
import androidx.compose.material.icons.outlined.ViewAgenda
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun ChangeLayoutButton(
    enableGridView: Boolean,
    onClick: () -> Unit
) {
    val layoutIcon =
        if (enableGridView) Icons.Outlined.ViewAgenda else Icons.Outlined.GridView

    IconButton(
        onClick = onClick,
    ) {
        Icon(
            imageVector = layoutIcon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.surfaceTint
        )
    }
}

@Preview(name = "ChangeLayoutButton")
@Composable
private fun PreviewChangeLayoutButton() {
    ChangeLayoutButton(enableGridView = false, onClick = { })
}