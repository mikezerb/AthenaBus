package com.example.athenabus.presentation.main_screen.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.athenabus.R

@Composable
fun DrawerHeader(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = { },
) {
    Surface(
        shape = MaterialTheme.shapes.extraLarge,
        color = MaterialTheme.colorScheme.primary,
        modifier = modifier
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = stringResource(id = R.string.app_name),
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(horizontal = 28.dp, vertical = 24.dp)
            )
            Row(
                modifier = Modifier.padding(20.dp)
            ) {
                IconButton(onClick = onClick) {
                    Icon(
                        imageVector = Icons.Filled.Info,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun HeaderPreview() {
    Column(modifier = Modifier.fillMaxSize()) {
        DrawerHeader(Modifier.fillMaxWidth())
    }


}
