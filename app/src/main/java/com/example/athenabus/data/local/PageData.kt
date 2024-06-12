package com.example.athenabus.data.local

import com.example.athenabus.R
import com.example.athenabus.presentation.onboarding.Page

object PageData {
    val pages = listOf(
        Page(
            R.string.onboard_title_1,
            R.string.onboard_desc_1,
            R.drawable.people_map
        ),
        Page(
            R.string.onboard_title_2,
            R.string.onboard_desc_2,
            R.drawable.location
        ),
        Page(
            R.string.onboard_title_3,
            R.string.onboard_desc_3,
            R.drawable.man_phone
        )
    )
}