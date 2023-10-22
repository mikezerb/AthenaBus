package com.example.athenabus.presentation.settings_screen.components

import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource

@Composable
fun BasicPreferenceItem(
    modifier: Modifier = Modifier,
    @StringRes title: Int,
    @StringRes description: Int,
    icon: @Composable (() -> Unit)? = null,
    onClick: () -> Unit
) {
    ListItem(
        modifier = modifier.clickable { onClick() },
        headlineContent = {
            Text(
                text = stringResource(id = title),
                style = MaterialTheme.typography.bodyLarge
            )
        },
        supportingContent = {
            Text(
                text = stringResource(id = description),
                style = MaterialTheme.typography.bodyMedium
            )
        },
        leadingContent = icon,
    )
}