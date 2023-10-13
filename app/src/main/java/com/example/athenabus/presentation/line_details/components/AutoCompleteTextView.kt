package com.example.athenabus.presentation.line_details.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.ExposedDropdownMenuDefaults.TrailingIcon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AutoCompleteTextField(
    modifier: Modifier = Modifier,
    initialText: String,
    itemList: List<String> = emptyList(),
    onQuery: (String) -> Unit = { },
    onClearResults: () -> Unit = { },
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedOption by remember {
        if (itemList.isNotEmpty())
            mutableStateOf(itemList[0])
        else mutableStateOf("")
    }

    ExposedDropdownMenuBox(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
    ) {
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor(),
            value = selectedOption,
            onValueChange = { },
            label = { Text(text = initialText) },
            readOnly = true,
            trailingIcon = {
                TrailingIcon(expanded = expanded)
            },
            textStyle = MaterialTheme.typography.titleSmall,
            colors = ExposedDropdownMenuDefaults.textFieldColors()
        )
        ExposedDropdownMenu(
            modifier = Modifier.fillMaxWidth().padding(8.dp),
            expanded = expanded,
            onDismissRequest = { expanded = false }
        )
        {
            itemList.forEach { item ->
                DropdownMenuItem(
                    modifier = Modifier.fillMaxWidth(),
                    text = {
                        Text(text = item)
                    },
                    onClick = {
                        selectedOption = item
                        expanded = false
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding

                )

            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    val options = listOf("Option 1", "Option 2", "Option 3", "Option 4", "Option 5")
    Column(Modifier.fillMaxSize()) {
        AutoCompleteTextField(initialText = "Choose Direction", itemList = options)

    }
}
