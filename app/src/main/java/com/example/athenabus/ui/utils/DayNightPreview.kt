package com.example.athenabus.ui.utils

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.ui.tooling.preview.Preview

@Preview(
    name = "Day",
    showBackground = true
)
@Preview(
    name = "Night",
    uiMode = UI_MODE_NIGHT_YES,
    showBackground = true
)
annotation class DayNightPreview
