package com.example.athenabus.presentation.settings.components

import android.widget.Toast
import androidx.annotation.StringRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Contrast
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.athenabus.R

@Composable
fun OptionsPreference(
    modifier: Modifier = Modifier,
    @StringRes title: Int,
    options: List<String>,
    selected: Int = 0,
    onSelect: (Int) -> Unit,
    icon: @Composable (() -> Unit)? = null,
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
                .padding(vertical = 12.dp).padding(start = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                if (icon != null) {
                    Spacer(modifier = Modifier.width(8.dp))
                    icon()
                    Spacer(modifier = Modifier.width(8.dp))
                }
                Text(
                    text = stringResource(id = title),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
            Surface(
                shape = RoundedCornerShape(18.dp),
                modifier = Modifier.padding(8.dp),
                shadowElevation = 2.dp,
                color = MaterialTheme.colorScheme.surface
            ) {
                Row {
                    options.forEachIndexed { index, option ->
                        Text(
                            text = option,
                            style = MaterialTheme.typography.labelMedium,
                            color = if (index == selected) {
                                MaterialTheme.colorScheme.onPrimary
                            } else {
                                MaterialTheme.colorScheme.onSurface
                            },
                            modifier = Modifier
                                .clip(shape = RoundedCornerShape(18.dp))
                                .clickable {
                                    onSelect(index)
                                }
                                .background(
                                    if (index == selected) {
                                        MaterialTheme.colorScheme.primary
                                    } else {
                                        MaterialTheme.colorScheme.surface
                                    }
                                )
                                .padding(
                                    horizontal = if (index == selected) {
                                        10.dp
                                    } else {
                                        8.dp
                                    },
                                    vertical = 8.dp
                                ),
                        )
                    }
                }

            }

        }

    }

}

@Preview
@Composable
private fun PreviewOptionsPreference() {
    var selectedIndex by remember { mutableIntStateOf(1) }
    val list = listOf("Follow System", "Off", "On")
    val context = LocalContext.current
    Scaffold {
        OptionsPreference(
            modifier = Modifier.padding(it),
            title = R.string.theme_settings_theme_title,
            options = list,
            selected = selectedIndex,
            onSelect = { index ->
                selectedIndex = index
                Toast.makeText(context, "Selected ${list[index]}", Toast.LENGTH_SHORT).show()
            },
            icon = {
                Icon(
                    imageVector = Icons.Outlined.Contrast,
                    contentDescription = null
                )
            }
        )
    }
}