package com.example.athenabus.presentation.settings.components

import android.content.res.Configuration
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MiscellaneousServices
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.athenabus.R
import com.example.athenabus.ui.theme.AthenaBusTheme

@Composable
fun SettingsGroup(
    @StringRes title: Int,
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(modifier = Modifier.padding(vertical = 4.dp)) {
        Text(
            text = stringResource(id = title),
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.primary,
            modifier = modifier.padding(start = 16.dp, top = 16.dp, end = 16.dp)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(4)
        ) {
            Column {
                content()
            }
        }
    }

}

@Preview(name = "SettingsSection", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(name = "SettingsSection", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun PreviewSettingsSection() {
    AthenaBusTheme {
        Column(modifier = Modifier.background(MaterialTheme.colorScheme.background)) {
            SettingsGroup(title = R.string.default_setting_section) {
                BasicPreference(
                    icon = Icons.Default.MiscellaneousServices,
                    title = R.string.default_setting_title,
                    description = R.string.default_setting_desc,
                    onClick = { }
                )
                HorizontalDivider()
                BasicPreference(
                    title = R.string.default_setting_title,
                    description = R.string.default_setting_desc,
                    onClick = { }
                )
            }
        }
    }
}