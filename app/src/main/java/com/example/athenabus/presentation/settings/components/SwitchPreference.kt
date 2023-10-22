package com.example.athenabus.presentation.settings.components

import androidx.annotation.StringRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CircleNotifications
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.athenabus.R

@Composable
fun SwitchPreference(
    modifier: Modifier = Modifier,
    @StringRes title: Int,
    @StringRes subtitle: Int,
    icon: @Composable (() -> Unit)? = null,
    isChecked: Boolean = false,
    enabled: Boolean = true,
    onCheckedChange: (Boolean) -> Unit = {},
) {
    Card(
        modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.surfaceContainer),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp)
                .padding(start = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                if (icon != null) {
                    Spacer(modifier = Modifier.width(8.dp))
                    icon()
                    Spacer(modifier = Modifier.width(8.dp))
                }
                Column {
                    Text(
                        text = stringResource(id = title),
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Text(
                        text = stringResource(id = subtitle),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
            }
            Switch(
                modifier = Modifier.padding(end = 12.dp),
                checked = isChecked,
                onCheckedChange = onCheckedChange,
                enabled = enabled,
                thumbContent = if (isChecked) {
                    {
                        Icon(
                            imageVector = Icons.Filled.Check,
                            contentDescription = null,
                            modifier = Modifier.size(SwitchDefaults.IconSize),
                        )
                    }
                } else {
                    null
                }
            )
        }

    }
}

@Preview(name = "SwitchPreference")
@Composable
private fun PreviewSwitchPreference() {
    Column(modifier = Modifier.background(MaterialTheme.colorScheme.background)) {
        SettingsGroup(title = R.string.default_setting_section) {
            SwitchPreference(
                title = R.string.default_setting_title,
                subtitle = R.string.default_setting_desc
            )
            SwitchPreference(
                title = R.string.default_setting_title,
                subtitle = R.string.default_setting_desc,
                icon = {
                    Icon(imageVector = Icons.Default.CircleNotifications, contentDescription = null)
                }
            )
            BasicPreference(
                title = R.string.default_setting_title,
                description = R.string.default_setting_desc,
                onClick = { },
                icon = Icons.Default.CircleNotifications
            )
        }
    }
}
