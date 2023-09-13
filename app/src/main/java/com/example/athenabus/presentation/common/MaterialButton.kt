package com.example.athenabus.presentation.common

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun MaterialElevatedButton(
    text: String,
    onClick: () -> Unit
) {
    ElevatedButton(
        onClick = onClick,
        modifier = Modifier.padding(16.dp),
        colors = ButtonDefaults.buttonColors(
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
            containerColor = MaterialTheme.colorScheme.primaryContainer,
        )
    ) {
        Text(text = text)
    }

}

//@Preview(showBackground = false)
//@Preview(uiMode = UI_MODE_NIGHT_YES, showBackground = true)
//@Composable
//fun materialButtonPreview() {
//    AthenaBusTheme {
//        MaterialTextButton(
//            text = "Next",
//            onClick = { }
//        )
//    }
//}

@Composable
fun MaterialTextButton(
    text: String,
    onClick: () -> Unit
) {
    TextButton(
        onClick = onClick,
        modifier = Modifier.padding(16.dp)
    ) {
        Text(text = text)
    }
}