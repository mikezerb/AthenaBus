package com.example.athenabus.presentation.stop_arrival.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArrivalBottomSheet(
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit,
    sheetState: SheetState,
) {
    ModalBottomSheet(
        sheetState = sheetState,
        onDismissRequest = onDismiss
    ) {
        Text(text = "ArrivalBottomSheet")
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(name = "ArrivalBottomSheet")
@Composable
private fun PreviewArrivalBottomSheet() {
    val sheetState = rememberModalBottomSheetState()
    ArrivalBottomSheet(onDismiss = { }, sheetState = sheetState)
}