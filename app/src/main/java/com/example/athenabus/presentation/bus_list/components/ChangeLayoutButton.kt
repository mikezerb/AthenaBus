package com.example.athenabus.presentation.bus_list.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.GridView
import androidx.compose.material.icons.outlined.ViewAgenda
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.athenabus.R

@Composable
fun ChangeLayoutButton(
    enableGridView: Boolean,
    onClick: () -> Unit
) {
    val tint = LocalContentColor.current

    val oldLayoutIcon =
        if (enableGridView) painterResource(id = R.drawable.grid_btn_icon) else painterResource(id = R.drawable.list_btn_icon)

    val layoutIcon =
        if (enableGridView) Icons.Outlined.ViewAgenda else Icons.Outlined.GridView

    IconButton(
        onClick = onClick,
        modifier = Modifier.padding(4.dp)
    ) {
        Icon(imageVector = layoutIcon, contentDescription = null, tint = tint)
    }
}

@Preview(name = "ChangeLayoutButton")
@Composable
private fun PreviewChangeLayoutButton() {
    ChangeLayoutButton(enableGridView = false, onClick = { })
}