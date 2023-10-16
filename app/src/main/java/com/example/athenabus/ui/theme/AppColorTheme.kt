package com.example.athenabus.ui.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

class AppColorTheme {
    fun getTheme(appTheme: AppTheme, dark: Boolean = false): ColorScheme {
        return when (appTheme) {
            AppTheme.Green -> if (dark) greenDark else greenLight
            AppTheme.Blue -> if (dark) blueDark else blueLight
            AppTheme.Peach -> TODO()
            AppTheme.Yellow -> TODO()
            AppTheme.Lavender -> TODO()
            AppTheme.BlackAndWhite -> TODO()
        }
    }

    private val greenLight = lightColorScheme(
        primary = Color(0xFF1F6D19),
        onPrimary = Color(0xFFFFFFFF),
        primaryContainer = Color(0xFFA4F791),
        onPrimaryContainer = Color(0xFF002200),
        secondary = Color(0xFF54634E),
        onSecondary = Color(0xFFFFFFFF),
        secondaryContainer = Color(0xFFD7E8CD),
        onSecondaryContainer = Color(0xFF121F0E),
        tertiary = Color(0xFF386569),
        onTertiary = Color(0xFFFFFFFF),
        tertiaryContainer = Color(0xFFBCEBEE),
        onTertiaryContainer = Color(0xFF002022),
        error = Color(0xFFBA1A1A),
        errorContainer = Color(0xFFFFDAD6),
        onError = Color(0xFFFFFFFF),
        onErrorContainer = Color(0xFF410002),
        background = Color(0xFFFCFDF6),
        onBackground = Color(0xFF1A1C18),
        surface = Color(0xFFFCFDF6),
        onSurface = Color(0xFF1A1C18),
        surfaceVariant = Color(0xFFDFE4D8),
        onSurfaceVariant = Color(0xFF43483F),
        outline = Color(0xFF73796E),
        inverseOnSurface = Color(0xFFF1F1EB),
        inverseSurface = Color(0xFF2F312D),
        inversePrimary = Color(0xFF89DA78),
        surfaceTint = Color(0xFF1D461A),
    )

    private val greenDark = darkColorScheme(
        primary = Color(0xFF89DA78),
        onPrimary = Color(0xFF003A01),
        primaryContainer = Color(0xFF005302),
        onPrimaryContainer = Color(0xFFA4F791),
        secondary = Color(0xFFBBCBB2),
        onSecondary = Color(0xFF263422),
        secondaryContainer = Color(0xFF3C4B37),
        onSecondaryContainer = Color(0xFFD7E8CD),
        tertiary = Color(0xFFA0CFD2),
        onTertiary = Color(0xFF00373A),
        tertiaryContainer = Color(0xFF1E4D51),
        onTertiaryContainer = Color(0xFFBCEBEE),
        error = Color(0xFFFFB4AB),
        errorContainer = Color(0xFF93000A),
        onError = Color(0xFF690005),
        onErrorContainer = Color(0xFFFFDAD6),
        background = Color(0xFF1A1C18),
        onBackground = Color(0xFFE2E3DD),
        surface = Color(0xFF1A1C18),
        onSurface = Color(0xFFE2E3DD),
        surfaceVariant = Color(0xFF43483F),
        onSurfaceVariant = Color(0xFFC3C8BC),
        outline = Color(0xFF8D9387),
        inverseOnSurface = Color(0xFF1A1C18),
        inverseSurface = Color(0xFFE2E3DD),
        inversePrimary = Color(0xFF1F6D19),
        surfaceTint = Color(0xFF55944F)
    )

    private val blueLight = lightColorScheme(
        primary = md_theme_blue_light_primary,
        onPrimary = md_theme_blue_light_onPrimary,
        primaryContainer = md_theme_blue_light_primaryContainer,
        onPrimaryContainer = md_theme_blue_light_onPrimaryContainer,
        secondary = md_theme_blue_light_secondary,
        onSecondary = md_theme_blue_light_onSecondary,
        secondaryContainer = md_theme_blue_light_secondaryContainer,
        onSecondaryContainer = md_theme_blue_light_onSecondaryContainer,
        tertiary = md_theme_blue_light_tertiary,
        onTertiary = md_theme_blue_light_onTertiary,
        tertiaryContainer = md_theme_blue_light_tertiaryContainer,
        onTertiaryContainer = md_theme_blue_light_onTertiaryContainer,
        error = md_theme_blue_light_error,
        errorContainer = md_theme_blue_light_errorContainer,
        onError = Color(0xFFFFFFFF),
        onErrorContainer = Color(0xFF410002),
        background = md_theme_blue_light_background,
        onBackground = md_theme_blue_light_onBackground,
        surface = md_theme_blue_light_surface,
        onSurface = md_theme_blue_light_onSurface,
        surfaceVariant = md_theme_blue_light_surfaceVariant,
        onSurfaceVariant = md_theme_blue_light_onSurfaceVariant,
        outline = md_theme_blue_light_outline,
        outlineVariant = Color(0xFFC4C6CF),
        inverseOnSurface = md_theme_blue_light_inverseOnSurface,
        inverseSurface = md_theme_blue_light_inverseSurface,
        inversePrimary = md_theme_blue_light_inversePrimary,
        surfaceTint = md_theme_blue_light_surfaceTint,
        scrim = md_theme_blue_light_scrim,
    )

    private val blueDark = darkColorScheme(
        primary = md_theme_blue_dark_primary,
        onPrimary = md_theme_blue_dark_onPrimary,
        primaryContainer = md_theme_blue_dark_primaryContainer,
        onPrimaryContainer = md_theme_blue_dark_onPrimaryContainer,
        secondary = md_theme_blue_dark_secondary,
        onSecondary = md_theme_blue_dark_onSecondary,
        secondaryContainer = md_theme_blue_dark_secondaryContainer,
        onSecondaryContainer = md_theme_blue_dark_onSecondaryContainer,
        tertiary = md_theme_blue_dark_tertiary,
        onTertiary = md_theme_blue_dark_onTertiary,
        tertiaryContainer = md_theme_blue_dark_tertiaryContainer,
        onTertiaryContainer = md_theme_blue_dark_onTertiaryContainer,
        error = md_theme_blue_dark_error,
        errorContainer = md_theme_blue_dark_errorContainer,
        background = md_theme_blue_dark_background,
        onBackground = md_theme_blue_dark_onBackground,
        surface = md_theme_blue_dark_surface,
        onSurface = md_theme_blue_dark_onSurface,
        surfaceVariant = md_theme_blue_dark_surfaceVariant,
        onSurfaceVariant = md_theme_blue_dark_onSurfaceVariant,
        outline = md_theme_blue_dark_outline,
        inverseOnSurface = md_theme_blue_dark_inverseOnSurface,
        inverseSurface = md_theme_blue_dark_inverseSurface,
        inversePrimary = md_theme_blue_dark_inversePrimary,
        surfaceTint = md_theme_blue_dark_surfaceTint,
        scrim = md_theme_blue_dark_scrim,
        onError = Color(0xFF690005),
        onErrorContainer = Color(0xFFFFDAD6),
        outlineVariant = Color(0xFF43474E),
    )


    enum class AppTheme {
        Green,
        Blue,
        Peach,
        Yellow,
        Lavender,
        BlackAndWhite
    }
}
