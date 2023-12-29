package com.example.athenabus.domain.model

import androidx.compose.runtime.Stable
import com.example.athenabus.data.local.LineCategory

@Stable
data class Line(
    val LineCode: String,
    val LineID: String,
    val LineDescr: String,
    val LineDescrEng: String,
    val isFavorite: Boolean,
    var Category: LineCategory = LineCategory.UNKNOWN
)