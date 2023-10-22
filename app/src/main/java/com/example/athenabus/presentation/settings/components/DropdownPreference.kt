package com.example.athenabus.presentation.settings.components

import android.widget.Toast
import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Contrast
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.athenabus.R

@Composable
fun DropdownPreference(
    modifier: Modifier = Modifier,
    @StringRes title: Int,
    @StringRes description: Int,
    options: List<String>,
    selected: Int = 0,
    onSelect: (Int) -> Unit,
    onDismiss: () -> Unit,
    onClick: () -> Unit,
    expanded: Boolean = false,
    icon: @Composable (() -> Unit)? = null,
) {
    Card(
        modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .clip(RoundedCornerShape(10.dp))
            .clickable { onClick() },
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            if (icon != null) {
                icon()
                Spacer(modifier = Modifier.width(8.dp))
            }
            Column(
                modifier = Modifier
                    .padding(8.dp),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = stringResource(id = title),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Text(
                    text = stringResource(id = description),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onBackground
                )
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = onDismiss,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    options.forEachIndexed { index, option ->
                        DropdownMenuItem(
                            text = { Text(text = option) },
                            onClick = {
                                onSelect(index)
                                onDismiss()
                            })
                    }
                }
            }
            Spacer(modifier = Modifier.weight(1f))
            Text(
                modifier = Modifier.padding(4.dp),
                text = options[selected],
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground
            )
        }
    }
}

@Preview(name = "DropdownPreference")
@Composable
private fun PreviewDropdownPreference() {
    var expanded by remember { mutableStateOf(false) }
    var selectedIndex by remember { mutableIntStateOf(0) }
    val list = listOf("Option1", "option2")
    val context = LocalContext.current
    Scaffold {
        DropdownPreference(
            modifier = Modifier.padding(it),
            title = R.string.default_setting_title,
            description = R.string.default_setting_desc,
            options = list,
            expanded = expanded,
            selected = selectedIndex,
            onSelect = { index ->
                selectedIndex = index
                expanded = false
                Toast.makeText(context, "Selected ${list[index]}", Toast.LENGTH_SHORT).show()
            },
            onDismiss = { expanded = false },
            onClick = { expanded = true },
            icon = {
                Icon(
                    imageVector = Icons.Outlined.Contrast,
                    contentDescription = null
                )
            }
        )
    }

}