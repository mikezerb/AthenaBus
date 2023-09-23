package com.example.athenabus.presentation.search_screen.components

import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.athenabus.R
import com.example.athenabus.presentation.theme.AthenaBusTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(

) {
    var text by remember { mutableStateOf("") }
    var active by remember { mutableStateOf(false) }

    SearchBar(
        query = text,
        onQueryChange = { text = it },
        onSearch = { newQuery -> Log.d("search", "New search $newQuery") },
        active = active,
        onActiveChange = { active = it },
        placeholder = { Text(text = stringResource(R.string.search_placeholder)) },
        leadingIcon = { Icon(imageVector = Icons.Filled.Search, contentDescription = null) },
        trailingIcon = {
            if (active) {
                IconButton(
                    onClick = {
                        if (text.isNotEmpty()) text = "" else active = false
                    }

                ) {
                    Icon(imageVector = Icons.Filled.Close, contentDescription = null)

                }
            } else null
        },
        modifier = Modifier.padding(12.dp)

    ) {

    }
}

@Preview
@Composable
fun Prev() {
    AthenaBusTheme {
        SearchBar()
    }
}