package com.example.athenabus.presentation.settings

import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Brightness4
import androidx.compose.material.icons.outlined.CloudUpload
import androidx.compose.material.icons.outlined.ColorLens
import androidx.compose.material.icons.outlined.Contrast
import androidx.compose.material.icons.outlined.Language
import androidx.compose.material.icons.outlined.PrivacyTip
import androidx.compose.material.icons.rounded.DarkMode
import androidx.compose.material.icons.rounded.LightMode
import androidx.compose.material.icons.rounded.MotionPhotosAuto
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.os.LocaleListCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.athenabus.R
import com.example.athenabus.common.Constants
import com.example.athenabus.presentation.settings.components.BasicPreferenceItem
import com.example.athenabus.presentation.settings.components.ChoicePreference
import com.example.athenabus.presentation.settings.components.PreferenceSection
import com.example.athenabus.presentation.settings.components.SwitchListItem

fun supportsDynamic(): Boolean = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S

private fun changeLanguage(s: String) {
    //code to change the app language.
    AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags(s))
} // this AppCompatDelegate should be in main Thread, because its automatically restarts the activity.

@Composable
fun SettingsScreen(
    navController: NavController = rememberNavController(),
    themeViewModel: ThemeViewModel = hiltViewModel()
) {
    // Observe the dark theme setting
    val themeState by themeViewModel.themeState.collectAsState()
    var isOtherMode by remember { mutableStateOf(false) }
    var langFlag by remember { mutableStateOf(false) }

    val context = LocalContext.current

    val themeOptions = listOf(
        Icons.Rounded.MotionPhotosAuto,
        Icons.Rounded.LightMode,
        Icons.Rounded.DarkMode
    )
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        PreferenceSection(title = R.string.theme_settings_theme_section) {
            ChoicePreference(
                title = R.string.theme_settings_theme_title,
                options = themeOptions,
                selected = themeState.appTheme,
                onSelect = { index ->
                    themeViewModel.setAppTheme(index)
                },
                icon = {
                    Icon(
                        imageVector = Icons.Outlined.Brightness4,
                        contentDescription = null
                    )
                }
            )
            HorizontalDivider()
            if (themeState.appTheme == 2 || (themeState.appTheme == 0 && isSystemInDarkTheme())) {
                SwitchListItem(
                    title = R.string.theme_settings_amoled_theme_title,
                    subtitle = R.string.theme_settings_theme_subtitle,
                    icon = {
                        Icon(
                            imageVector = Icons.Outlined.Contrast,
                            contentDescription = null
                        )
                    },
                    isChecked = themeState.isAmoledMode,
                    onCheckedChange = { themeViewModel.setAmoledBlack() }
                )
                HorizontalDivider()
            }
            if (supportsDynamic()) {
                SwitchListItem(
                    title = R.string.theme_settings_color_title,
                    subtitle = R.string.theme_settings_color_subtitle,
                    icon = {
                        Icon(
                            imageVector = Icons.Outlined.ColorLens,
                            contentDescription = null
                        )
                    },
                    isChecked = themeState.isDynamicMode,
                    onCheckedChange = { themeViewModel.toggleDynamicColors() }
                )

            }
        }
        PreferenceSection(title = R.string.default_setting_section) {
            SwitchListItem(
                title = R.string.default_setting_title,
                subtitle = R.string.default_setting_desc,
                icon = {
                    Icon(
                        imageVector = Icons.Outlined.CloudUpload,
                        contentDescription = null
                    )
                },
                isChecked = isOtherMode,
                onCheckedChange = { isOtherMode = !isOtherMode }
            )
            HorizontalDivider()
            SwitchListItem(
                title = R.string.setting_lang_title,
                subtitle = R.string.setting_lang_desc,
                icon = {
                    Icon(
                        imageVector = Icons.Outlined.Language,
                        contentDescription = null
                    )
                },
                isChecked = langFlag,
                onCheckedChange = {
                    if (langFlag) {
                        changeLanguage("en")
                    } else {
                        changeLanguage("el")
                    }
                    langFlag = !langFlag
                }
            )
        }
        PreferenceSection(title = R.string.product_settings_product_section) {
            BasicPreferenceItem(
                title = R.string.product_settings_github_title,
                description = R.string.product_settings_github_subtitle,
                onClick = {
                    val intent = Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse(Constants.GITHUB_LINK)
                    )
                    context.startActivity(intent)
                },
                icon = {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.github_icon),
                        contentDescription = null
                    )
                }
            )
            HorizontalDivider()
            BasicPreferenceItem(
                title = R.string.product_settings_privacy_title,
                description = R.string.product_settings_privacy_subtitle,
                onClick = {
                    val intent = Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse(Constants.PRIVACY_LINK)
                    )
                    context.startActivity(intent)
                },
                icon = {
                    Icon(
                        imageVector = Icons.Outlined.PrivacyTip,
                        contentDescription = null
                    )
                }
            )
        }
    }
}

@Preview(name = "SettingsScreen")
@Composable
private fun PreviewSettingsScreen() {
    SettingsScreen()
}
