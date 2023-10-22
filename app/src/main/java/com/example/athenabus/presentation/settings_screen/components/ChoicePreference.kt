package com.example.athenabus.presentation.settings_screen.components

import androidx.annotation.StringRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Brightness4
import androidx.compose.material.icons.rounded.DarkMode
import androidx.compose.material.icons.rounded.LightMode
import androidx.compose.material.icons.rounded.MotionPhotosAuto
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.athenabus.R

@Composable
fun ChoicePreference(
    @StringRes title: Int,
    options: List<ImageVector>,
    selected: Int = 0,
    onSelect: (Int) -> Unit,
    icon: @Composable (() -> Unit)? = null,
) {
    ListItem(
        headlineContent = {
            Text(
                text = stringResource(id = title),
                style = MaterialTheme.typography.bodyLarge
            )
        },
        leadingContent = icon,
        trailingContent = {
            Surface(
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.surfaceContainer),
                color = MaterialTheme.colorScheme.surface
            ) {
                Row {
                    options.forEachIndexed { index, icon ->
                        IconButton(
                            modifier = Modifier
                                .background(
                                    if (index == selected) {
                                        MaterialTheme.colorScheme.primary
                                    } else {
                                        MaterialTheme.colorScheme.surface
                                    }
                                ),
                            onClick = { onSelect(index) }
                        ) {
                            Icon(
                                modifier = Modifier.background(
                                    if (index == selected) {
                                        MaterialTheme.colorScheme.primary
                                    } else {
                                        MaterialTheme.colorScheme.surface
                                    }
                                ),
                                tint = if (index == selected) {
                                    MaterialTheme.colorScheme.onPrimary
                                } else {
                                    MaterialTheme.colorScheme.onSurface
                                },
                                imageVector = icon,
                                contentDescription = null
                            )
                        }
                    }
                }
            }
        }
    )

}

@Preview
@Composable
private fun PreviewChoicePreference() {
    var selectedIndex by remember { mutableIntStateOf(2) }
    val list =
        listOf(Icons.Rounded.MotionPhotosAuto, Icons.Rounded.LightMode, Icons.Rounded.DarkMode)
    Column {
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
    }
}