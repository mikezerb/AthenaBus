package com.example.athenabus.presentation.settings

import android.app.LocaleManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.LocaleList
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Brightness4
import androidx.compose.material.icons.outlined.BuildCircle
import androidx.compose.material.icons.outlined.ColorLens
import androidx.compose.material.icons.outlined.Contrast
import androidx.compose.material.icons.outlined.PrivacyTip
import androidx.compose.material.icons.outlined.Translate
import androidx.compose.material.icons.rounded.DarkMode
import androidx.compose.material.icons.rounded.LightMode
import androidx.compose.material.icons.rounded.MotionPhotosAuto
import androidx.compose.material.icons.rounded.Refresh
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.os.LocaleListCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.athenabus.R
import com.example.athenabus.common.Constants
import com.example.athenabus.di.AppLanguage
import com.example.athenabus.presentation.about.getAppVersion
import com.example.athenabus.presentation.settings.components.BasicPreferenceItem
import com.example.athenabus.presentation.settings.components.ChoicePreference
import com.example.athenabus.presentation.settings.components.LocaleDropdownMenuPreference
import com.example.athenabus.presentation.settings.components.PreferenceSection
import com.example.athenabus.presentation.settings.components.SwitchListItem

fun supportsDynamic(): Boolean = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S

private fun changeLanguage(context: Context, code: AppLanguage) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        context.getSystemService(LocaleManager::class.java).applicationLocales =
            LocaleList.forLanguageTags(code.selectedLangCode)
    } else {
        val appLocale: LocaleListCompat = LocaleListCompat.forLanguageTags(code.selectedLangCode)
        AppCompatDelegate.setApplicationLocales(appLocale)
    }

}

@Composable
fun SettingsScreen(
    navController: NavController = rememberNavController(),
    themeViewModel: ThemeViewModel = hiltViewModel(),
    settingsViewModel: SettingsViewModel = hiltViewModel(),
) {
    // Observe the dark theme setting
    val themeState by themeViewModel.themeState.collectAsState()
    val settingsState by settingsViewModel.settingState.collectAsState()
    var isOtherMode by remember { mutableStateOf(false) }

    val localeOptions: List<AppLanguage> = listOf(
        AppLanguage(selectedLang = stringResource(id = R.string.el), selectedLangCode = "el"),
        AppLanguage(selectedLang = stringResource(id = R.string.en), selectedLangCode = "en"),
    )
    Log.d("LANG", settingsState.lanCode)

    val selectedLang = when (settingsState.lanCode) {
        "en" -> 1
        else -> 0
    }

    var langExpanded by remember { mutableStateOf(false) }

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

        PreferenceSection(title = R.string.application_settings_section) {
            LocaleDropdownMenuPreference(
                title = R.string.setting_lang_title,
                options = localeOptions,
                onSelect = { i ->
                    langExpanded = false
                    changeLanguage(
                        context,
                        AppLanguage(
                            selectedLang = localeOptions[i].selectedLang,
                            selectedLangCode = localeOptions[i].selectedLangCode
                        )
                    )
                    settingsViewModel.saveSelectedLang(
                        AppLanguage(
                            selectedLang = localeOptions[i].selectedLang,
                            selectedLangCode = localeOptions[i].selectedLangCode
                        )
                    )
                },
                onDismiss = { langExpanded = false },
                onClick = { langExpanded = true },
                expanded = langExpanded,
                selected = selectedLang,
                icon = {
                    Icon(
                        imageVector = Icons.Outlined.Translate,
                        contentDescription = null
                    )
                }
            )

            HorizontalDivider()

            BasicPreferenceItem(
                title = R.string.application_refresh_setting_title,
                description = R.string.application_refresh_setting_desc,
                onClick = {
                    // TODO: Update data from API and save to local DB
                    Toast.makeText(context, "Update data", Toast.LENGTH_SHORT).show()
                },
                icon = {
                    Icon(
                        imageVector = Icons.Rounded.Refresh,
                        contentDescription = null
                    )
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
            HorizontalDivider()

            BasicPreferenceItem(
                title = R.string.product_settings_version_title,
                subtitle = getAppVersion(context = context)?.versionName.toString(),
                icon = {
                    Icon(
                        imageVector = Icons.Outlined.BuildCircle,
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
