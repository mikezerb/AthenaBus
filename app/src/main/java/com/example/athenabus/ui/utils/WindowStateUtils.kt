package com.example.athenabus.ui.utils

/**
 * Different type of navigation supported by app depending on device size and state.
 */
enum class ReplyNavigationType {
    BOTTOM_NAVIGATION, NAVIGATION_RAIL, PERMANENT_NAVIGATION_DRAWER
}

/**
 * Different position of navigation content inside Navigation Rail, Navigation Drawer depending on device size and state.
 */
enum class ReplyNavigationContentPosition {
    TOP, CENTER
}

/**
 * App Content shown depending on device size and state.
 */
enum class ReplyContentType {
    SINGLE_PANE, DUAL_PANE
}