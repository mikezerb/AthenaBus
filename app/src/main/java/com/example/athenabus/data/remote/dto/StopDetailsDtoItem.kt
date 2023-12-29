package com.example.athenabus.data.remote.dto

data class StopDetailsDtoItem(
    val isTerminal: Any,
    val stop_descr: String,
    val stop_descr_matrix_eng: String,
    val stop_heading: String,
    val stop_id: String,
    val stop_lat: String,
    val stop_lng: String
)