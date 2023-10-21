package com.example.athenabus.presentation.settings_screen.components

import android.content.res.Configuration
import androidx.annotation.StringRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.athenabus.R
import com.example.athenabus.ui.theme.AthenaBusTheme

@Composable
fun BasicPreference(
    modifier: Modifier = Modifier,
    @StringRes title: Int,
    @StringRes description: Int,
    icon: ImageVector? = null,
    onClick: () -> Unit
) {
    Card(
        modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .clickable { onClick() },
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.surfaceContainer),
    ) {
        Row(
            modifier = Modifier.padding(vertical = 12.dp).padding(start = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (icon != null) {
                Spacer(modifier = Modifier.width(8.dp))
                Icon(
                    imageVector = icon,
                    contentDescription = stringResource(id = description),
                )
                Spacer(modifier = Modifier.width(8.dp))
            }
            Column {
                Text(
                    text = stringResource(id = title),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Text(
                    text = stringResource(id = description),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        }


    }
}

@Preview(
    name = "BasicPreference Light mode",
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    showBackground = true
)
@Preview(
    name = "BasicPreference Dark mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true
)
@Composable
private fun PreviewBasicPreference() {
    BasicPreference(
        title = R.string.default_setting_title,
        description = R.string.default_setting_desc,
        onClick = { }
    )
}

@Preview(
    name = "Icon BasicPreference Light mode",
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    showBackground = true
)
@Preview(
    name = "Icon BasicPreference Dark mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true
)
@Composable
private fun PreviewBasicWithIconPreference() {
    Column(modifier = Modifier.background(MaterialTheme.colorScheme.background)) {
        SettingsGroup(title = R.string.default_setting_section) {
            BasicPreference(
                title = R.string.default_setting_title,
                description = R.string.default_setting_desc,
                icon = Icons.Default.LightMode,
                onClick = { }
            )
        }
    }
}