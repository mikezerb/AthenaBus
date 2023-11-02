@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.athenabus.presentation.settings.components

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Translate
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.athenabus.R
import com.example.athenabus.di.AppLanguage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LocaleDropdownMenuPreference(
    modifier: Modifier = Modifier,
    @StringRes title: Int,
    @StringRes description: Int? = null,
    options: List<AppLanguage>,
    selected: Int = 0,
    onSelect: (Int) -> Unit,
    onDismiss: () -> Unit,
    onClick: () -> Unit,
    expanded: Boolean = false,
    icon: @Composable (() -> Unit)? = null,
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
            if (description != null) {
                Text(
                    text = stringResource(id = description),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        },
        leadingContent = icon,
        trailingContent = {
            Row {
                Text(
                    modifier = Modifier.padding(4.dp),
                    text = options[selected].selectedLang,
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.primary
                )
                ExposedDropdownMenuDefaults.TrailingIcon(
                    expanded = expanded
                )
            }
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = onDismiss,
                modifier = Modifier
                    .padding(8.dp)
            ) {
                options.forEachIndexed { index, option ->
                    DropdownMenuItem(
                        text = { Text(text = option.selectedLang) },
                        onClick = {
                            onSelect(index)
                            onDismiss()
                        })
                }
            }

        }
    )

}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    val localeOptions : List<AppLanguage> = listOf(
        AppLanguage(selectedLang = stringResource(id = R.string.el), selectedLangCode = "el"),
        AppLanguage(selectedLang = stringResource(id = R.string.en), selectedLangCode = "en"),
    )

    var expanded by remember { mutableStateOf(false) }
    var selectedIndex by remember { mutableIntStateOf(0) }
    val context = LocalContext.current
    Column(Modifier.fillMaxWidth()) {
        LocaleDropdownMenuPreference(
            title = R.string.setting_lang_title,
            description = R.string.setting_lang_desc,
            options = localeOptions,
            expanded = expanded,
            selected = selectedIndex,
            onSelect = { index ->
                selectedIndex = index
                expanded = false
                Toast.makeText(
                    context,
                    "Selected ${localeOptions[index].selectedLang} + ${localeOptions[index].selectedLangCode}",
                    Toast.LENGTH_SHORT
                ).show()
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