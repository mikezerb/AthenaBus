package com.example.athenabus.presentation.settings.components

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
    @StringRes description: Int? = null,
    subtitle: String? = null,
    icon: @Composable (() -> Unit)? = null,
    onClick: (() -> Unit)? = null
) {
    ListItem(
        modifier = if (onClick != null) {
            Modifier.clickable { onClick() }
        } else {
            Modifier
        },
        headlineContent = {
            Text(
                text = stringResource(id = title),
                style = MaterialTheme.typography.bodyLarge
            )
        },
        supportingContent = {
            if (description != null || subtitle != null) {
                Text(
                    text = description?.let { stringResource(id = it) } ?: subtitle.orEmpty(),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        },
        leadingContent = icon,
    )
}