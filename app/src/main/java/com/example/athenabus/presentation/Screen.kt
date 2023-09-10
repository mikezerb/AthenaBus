package com.example.athenabus.presentation

sealed class Screen(val route: String) {

    object BusLineListScreen: Screen("bus_lines_list_screen")
    object BusLineDetailsScreen: Screen("bus_lines_detail")
}