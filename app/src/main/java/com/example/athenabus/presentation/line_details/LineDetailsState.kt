package com.example.athenabus.presentation.line_details

import com.example.athenabus.domain.model.Line

data class LineDetailsState(
    val line: Line? = null,
    val isLoading: Boolean = false,
    val error: String = ""
)
