package com.example.athenabus.presentation.settings

import java.util.Locale

fun determineLanCode(): String {
    return when (val defaultLanguage = Locale.getDefault().language) {
        "el", "en" -> defaultLanguage
        else -> "en" // Default to "en" for other languages
    }
}

data class SettingsState(
    val lanCode: String = determineLanCode()
)
