package com.example.athenabus.presentation.bus_list.components

import androidx.annotation.StringRes

data class TypeFilter(
    @StringRes val label: Int,
    val selected: Boolean
)
