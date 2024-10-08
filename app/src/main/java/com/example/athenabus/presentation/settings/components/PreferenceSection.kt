package com.example.athenabus.presentation.settings.components

import android.widget.Toast
import androidx.annotation.StringRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Brightness4
import androidx.compose.material.icons.outlined.Brightness4
import androidx.compose.material.icons.outlined.Contrast
import androidx.compose.material.icons.outlined.Translate
import androidx.compose.material.icons.rounded.DarkMode
import androidx.compose.material.icons.rounded.LightMode
import androidx.compose.material.icons.rounded.MotionPhotosAuto
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.athenabus.R
import com.example.athenabus.di.AppLanguage

@Composable
fun PreferenceSection(
    modifier: Modifier = Modifier,
    @StringRes title: Int,
    content: @Composable ColumnScope.() -> Unit
) {
    Card(
        modifier = modifier.padding(4.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
        ),
        border = BorderStroke(width = 1.dp, color = MaterialTheme.colorScheme.outlineVariant)
    ) {
        Text(
            text = stringResource(id = title),
            modifier = Modifier
                .padding(start = 16.dp, top = 12.dp, bottom = 4.dp),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.primary
        )
        Column {
            content()
        }
    }
}

@Preview
@Composable
private fun PreviewPreferenceSection() {
    Scaffold {
        var isChecked by remember { mutableStateOf(false) }
        var selectedIndex by remember { mutableIntStateOf(2) }
        val list =
            listOf(Icons.Rounded.MotionPhotosAuto, Icons.Rounded.LightMode, Icons.Rounded.DarkMode)

        var expanded by remember { mutableStateOf(false) }
        var dropdownIndex by remember { mutableIntStateOf(0) }
        val localeOptions: List<AppLanguage> = listOf(
            AppLanguage(selectedLang = stringResource(id = R.string.el), selectedLangCode = "el"),
            AppLanguage(selectedLang = stringResource(id = R.string.en), selectedLangCode = "en"),
        )
        val context = LocalContext.current
        Column {
            PreferenceSection(
                modifier = Modifier.padding(it),
                title = R.string.default_setting_section
            ) {
                ChoicePreference(
                    title = R.string.theme_settings_theme_title,
                    options = list,
                    selected = selectedIndex,
                    onSelect = { i -> selectedIndex = i },
                    icon = {
                        Icon(
                            imageVector = Icons.Outlined.Brightness4,
                            contentDescription = null
                        )
                    }
                )
                HorizontalDivider()
                BasicPreferenceItem(
                    title = R.string.application_refresh_setting_title,
                    description = R.string.application_refresh_setting_desc,
                    onClick = {
                        Toast.makeText(context, "Update data", Toast.LENGTH_SHORT).show()
                    },
                    icon = {
                        Icon(
                            imageVector = Icons.Rounded.Refresh,
                            contentDescription = null
                        )
                    }
                )
                HorizontalDivider()
                BasicPreferenceItem(
                    modifier = Modifier.background(MaterialTheme.colorScheme.surface),
                    title = R.string.default_setting_title,
                    description = R.string.default_setting_desc,
                    onClick = { }
                )
                HorizontalDivider()
            }
            PreferenceSection(
                modifier = Modifier.padding(it),
                title = R.string.default_setting_section
            ) {
                BasicPreferenceItem(
                    icon = {
                        Icon(
                            imageVector = Icons.Default.Brightness4,
                            contentDescription = null
                        )
                    },
                    title = R.string.default_setting_title,
                    description = R.string.default_setting_desc,
                    onClick = { }
                )
                HorizontalDivider()
                SwitchListItem(
                    title = R.string.theme_settings_amoled_theme_title,
                    subtitle = R.string.theme_settings_theme_subtitle,
                    icon = {
                        Icon(
                            imageVector = Icons.Outlined.Contrast,
                            contentDescription = null
                        )
                    },
                    isChecked = true,
                    onCheckedChange = { }
                )
                HorizontalDivider()
                LocaleDropdownMenuPreference(
                    title = R.string.setting_lang_title,
                    description = R.string.setting_lang_desc,
                    options = localeOptions,
                    expanded = expanded,
                    selected = dropdownIndex,
                    onSelect = { index ->
                        dropdownIndex = index
                        expanded = false
                        Toast.makeText(context, "Selected ${list[index]}", Toast.LENGTH_SHORT)
                            .show()
                    },
                    onDismiss = { expanded = false },
                    onClick = { expanded = true },
                    icon = {
                        Icon(
                            imageVector = Icons.Outlined.Translate,
                            contentDescription = null
                        )
                    }
                )
            }
        }
    }
}