package com.example.athenabus.presentation.bus_list.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.athenabus.R
import com.example.athenabus.ui.theme.AthenaBusTheme

@Composable
fun LinesSearchBar(
    searchQuery: String,
    onSearchChange: (String) -> Unit,
    onClearSearch: () -> Unit,
    modifier: Modifier = Modifier
) {
    // val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    OutlinedTextField(
        value = searchQuery,
        onValueChange = onSearchChange,
        placeholder = { Text(text = stringResource(id = R.string.search_place_holder)) },
        leadingIcon = {
            Icon(
                imageVector = Icons.Outlined.Search,
                contentDescription = stringResource(id = R.string.search_place_holder),
                tint = MaterialTheme.colorScheme.surfaceTint
            )
        },
        trailingIcon = {
            if (searchQuery.isNotEmpty()) {
                IconButton(
                    onClick = {
                        onClearSearch()
                    },
                    modifier = Modifier.padding(horizontal = 12.dp)
                ) {
                    Icon(imageVector = Icons.Outlined.Close, contentDescription = null)
                }
            }
        },
        shape = RoundedCornerShape(32.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.surface,
            unfocusedContainerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.8f),
            focusedBorderColor = MaterialTheme.colorScheme.surfaceTint.copy(alpha = 0.5f),
            unfocusedBorderColor = MaterialTheme.colorScheme.surfaceTint.copy(alpha = 0.5f)
        ),
        modifier = modifier,
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
//          onDone = { keyboardController?.hide() })    // this closes the keyboard without removing the focus
            onDone = { focusManager.clearFocus() }
        ),
        textStyle = MaterialTheme.typography.bodyLarge
    )
}

@Preview(name = "LinesSearchBar")
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
private fun PreviewLinesSearchBar() {
    AthenaBusTheme {
        var searchQuery by remember {
            mutableStateOf("608")
        }
        LinesSearchBar(
            searchQuery = searchQuery,
            onSearchChange = { },
            onClearSearch = { searchQuery = "" })
    }
}