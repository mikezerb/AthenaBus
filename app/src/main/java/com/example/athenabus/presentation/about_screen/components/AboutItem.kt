package com.example.athenabus.presentation.about_screen.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Code
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun AboutItem(
    modifier: Modifier = Modifier,
    title: String,
    subtitle: String? = null,
    icon: ImageVector,
    button: @Composable (Modifier) -> Unit = { },
) {
    ListItem(
        modifier = modifier.fillMaxWidth(),
        headlineContent = { Text(text = title) },
        leadingContent = { Icon(imageVector = icon, contentDescription = null) },
        supportingContent = { Text(text = subtitle.orEmpty()) },
        trailingContent = { button(Modifier.padding(start = 4.dp)) }
    )
}

@Preview
@Composable
private fun PreviewAboutItem() {
    AboutItem(title = "About me", subtitle = "little stuff about me", icon = Icons.Default.Code,
        button = {
            TextButton(onClick = { }) {
                Text(text = "View Profile")
            }
        })
}