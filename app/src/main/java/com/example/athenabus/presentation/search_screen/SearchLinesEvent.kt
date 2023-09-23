package com.example.athenabus.presentation.search_screen

sealed class SearchLinesEvent {
    object Refresh : SearchLinesEvent()
    data class OnSearchQueryChange(val query: String) : SearchLinesEvent()
}

