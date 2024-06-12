package com.example.athenabus.presentation.onboarding

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class Page(
    @StringRes val titleResId: Int,
    @StringRes val descriptionResId: Int,
    @DrawableRes val image: Int,
)


