package com.example.athenabus.presentation.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.athenabus.R

@Composable
fun EmptyScreen(
    modifier: Modifier = Modifier,
    title: String,
    content: @Composable () -> Unit = { }
) {
    val listFaces = listOf("(・へ・)", "(='X'=)", "(^-^*)", "(·_·)", "¯\\_ (ツ) _/¯", "(╯° □°）", "ಥ_ಥ")
    val emptyExpression by remember { mutableStateOf(listFaces.random()) }

    Column(
        modifier = modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = emptyExpression,
            style = MaterialTheme.typography.displaySmall,
            fontFamily = FontFamily(Font(R.font.poppins_regular)),
            color = MaterialTheme.colorScheme.surfaceTint
        )
        Text(
            modifier = Modifier.padding(vertical = 8.dp),
            text = title,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.surfaceTint
        )
        content()
    }
}

@Preview
@Composable
private fun PreviewEmptyScreen() {
    EmptyScreen(title = "No data here...")
}