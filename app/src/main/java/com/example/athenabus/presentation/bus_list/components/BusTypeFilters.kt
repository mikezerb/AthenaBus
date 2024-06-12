@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.athenabus.presentation.bus_list.components

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.ElevatedFilterChip
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.athenabus.R
import com.example.athenabus.data.local.LineCategory
import com.google.android.material.color.MaterialColors

@Composable
fun MultiLineFilters(
    modifier: Modifier = Modifier,
    selected: List<String>,
    categories: List<LineCategory>,
    onClick: (String) -> Unit,
) {
    val context = LocalContext.current

    LazyRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        items(categories) { item ->
            val customColors = FilterChipDefaults.elevatedFilterChipColors(
                containerColor = Color(
                    MaterialColors.harmonizeWithPrimary(
                        context,
                        item.color.toArgb()
                    )
                ),
                selectedContainerColor = Color(
                    MaterialColors.harmonizeWithPrimary(
                        context,
                        item.color.copy(alpha = 0.1f).toArgb()
                    )
                ),
                labelColor = Color.White
            )
            FilterChip(
                modifier = modifier,
                colors = customColors,
                onClick = { onClick(item.name) },
                label = {
                    Row(
                        horizontalArrangement = Arrangement.SpaceAround,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(imageVector = item.iconResId, contentDescription = null)
                        Text(context.getString(item.titleResId))
                    }
                },
                selected = selected.contains(item.name),
                leadingIcon = if (selected.contains(item.name)) {
                    {
                        Icon(
                            imageVector = Icons.Filled.Done,
                            contentDescription = "Done icon",
                            modifier = Modifier.size(FilterChipDefaults.IconSize)
                        )
                    }
                } else {
                    null
                },
            )
        }
    }
}

@Composable
fun SingleLineFilters(
    modifier: Modifier = Modifier,
    selected: String,
    categories: List<LineCategory>,
    onClick: (String) -> Unit,
) {
    val context = LocalContext.current
    LazyRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        items(categories) { item ->
            val customColors = FilterChipDefaults.elevatedFilterChipColors(
                containerColor = Color(
                    MaterialColors.harmonizeWithPrimary(
                        context,
                        item.color.toArgb()
                    )
                ),
                selectedContainerColor = Color(
                    MaterialColors.harmonizeWithPrimary(
                        context,
                        item.color.toArgb()
                    )
                ).copy(alpha = 0.4f),
                iconColor = Color(
                    MaterialColors.harmonizeWithPrimary(
                        context,
                        Color.White.toArgb()
                    )
                ),
                selectedLeadingIconColor = Color(
                    MaterialColors.harmonizeWithPrimary(
                        context,
                        Color.White.toArgb()
                    )
                ).copy(alpha = 0.4f),
                labelColor = Color.White
            )
            ElevatedFilterChip(
                modifier = modifier,
                colors = customColors,
                onClick = { onClick(item.name) },
                leadingIcon = {
                    Icon(
                        imageVector = item.iconResId,
                        contentDescription = null,
                        tint = Color.White
                    )
                },
                label = {
                    Text(context.getString(item.titleResId))
                },
                selected = (item.name == selected),
            )
        }
    }
}

@Preview
@Composable
private fun PreviewBusTypeFilters() {
    val singleFilters = listOf(
        stringResource(id = R.string.buses_chip_label),
        stringResource(id = R.string.trolley_chip_label),
    )
    val multiFilters = listOf(
        stringResource(id = R.string._24hour_chip_label),
        stringResource(id = R.string.night_chip_label),
        stringResource(id = R.string.aeroplane_chip_label),
        stringResource(id = R.string.express_chip_label),
    )
    val context = LocalContext.current
    val selectedItems = remember {
        mutableStateListOf("")
    }
    var selectedItem by remember {
        mutableStateOf("") // initially, first item is selected
    }

    val categories = listOf(
        LineCategory.BUS,
        LineCategory.TROLLEY
    )
    val secondaryCategories = listOf(
        LineCategory.HOUR_24,
        LineCategory.NIGHT,
        LineCategory.EXPRESS,
        LineCategory.AIRPORT,
    )

    Scaffold {
        Surface(
            modifier = Modifier.padding(4.dp),
            shape = RoundedCornerShape(8.dp),
            border = BorderStroke(
                width = 1.dp,
                color = MaterialTheme.colorScheme.surfaceContainer
            )
        ) {
            Column(Modifier.padding(it)) {
                SingleLineFilters(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp),
                    selected = selectedItem,
                    categories = categories,
                    onClick = { item ->
                        selectedItem = item
                        Toast.makeText(context, "Sel: $item", Toast.LENGTH_SHORT).show()
                    }
                )
                MultiLineFilters(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp),
                    selected = selectedItems,
                    categories = secondaryCategories,
                    onClick = { item ->
                        if (selectedItems.contains(item)) {
                            selectedItems.remove(item)
                        } else {
                            selectedItems.add(item)
                        }
                        Toast.makeText(context, "Sel: $item", Toast.LENGTH_SHORT).show()
                    }
                )
            }
        }
    }
}