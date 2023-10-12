package com.example.athenabus.presentation.common

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.athenabus.domain.model.Line

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LineDetailsAppBar(
    modifier: Modifier = Modifier,
    navigateUp: () -> Unit = { },
    onFavoriteClick: () -> Unit = { },
    onMapClick: () -> Unit = { },
    onMoreSettings: () -> Unit = { },
    line: Line,
) {
    LargeTopAppBar(
        modifier = modifier,
        title = {
            Column {
                Text(text = line.LineID)
                Text(
                    text = line.LineDescr,
                    maxLines = 1,
                    style = MaterialTheme.typography.titleMedium
                )
            }
        },
        actions = {
            IconButton(onClick = onMapClick) {
                Icon(imageVector = Icons.Default.Map, contentDescription = null)
            }
            IconButton(onClick = onFavoriteClick) {
                if (line.isFavorite)
                    Icon(imageVector = Icons.Default.Star, contentDescription = null)
                else {
                    Icon(imageVector = Icons.Default.StarBorder, contentDescription = null)
                }
            }
            IconButton(onClick = onMoreSettings) {
                Icon(imageVector = Icons.Default.MoreVert, contentDescription = null)
            }
        },
        navigationIcon = {
            IconButton(onClick = navigateUp) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back"
                )
            }
        },
    )
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Preview(name = "LineDetailsAppBar")
@Composable
private fun PreviewLineDetailsAppBar() {
    Scaffold {
        LineDetailsAppBar(
            line = Line(
                LineCode = "021",
                LineID = "2124",
                LineDescr = "ΣΤ. ΥΠΕΡ. ΛΕΩΦ. ΚΗΦΙΣΟΥ - ΟΜΟΝΟΙΑ (ΜΕΣΩ ΑΚΑΔ. ΠΛΑΤ.)(ΚΥΚΛΙΚΗ)",
                LineDescrEng = "",
                isFavorite = true
            )
        )
    }

}
