package com.example.athenabus.presentation.settings_screen

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
import androidx.compose.material.icons.outlined.CloudUpload
import androidx.compose.material.icons.outlined.ColorLens
import androidx.compose.material.icons.outlined.Contrast
import androidx.compose.material.icons.outlined.DarkMode
import androidx.compose.material.icons.outlined.Language
import androidx.compose.material.icons.outlined.PrivacyTip
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
import com.example.athenabus.presentation.settings_screen.components.BasicPreference
import com.example.athenabus.presentation.settings_screen.components.OptionsPreference
import com.example.athenabus.presentation.settings_screen.components.SettingsGroup
import com.example.athenabus.presentation.settings_screen.components.SwitchPreference

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
//    var expanded by remember { mutableStateOf(false) }
//    var selectedIndex by remember { mutableIntStateOf(0) }
    val options = listOf(
        stringResource(R.string.follow_system),
        stringResource(R.string.theme_off),
        stringResource(R.string.theme_on)
    )
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        SettingsGroup(title = R.string.theme_settings_theme_section) {
//            DropdownPreference(
//                title = R.string.theme_settings_theme_title,
//                description = R.string.theme_settings_theme_subtitle,
//                options = options,
//                expanded = expanded,
//                selected = selectedIndex,
//                onSelect = { index ->
//                    selectedIndex = index
//                    expanded = false
//                },
//                onDismiss = {
//                    expanded = false
//                },
//                onClick = { expanded = true },
//                icon = {
//                    Icon(
//                        imageVector = Icons.Outlined.DarkMode,
//                        contentDescription = null
//                    )
//                }
//            )
            OptionsPreference(
                title = R.string.theme_settings_theme_title,
                options = options,
                selected = themeState.appTheme,
                onSelect = { index ->
                    themeViewModel.setAppTheme(index)
                },
                icon = {
                    Icon(
                        imageVector = Icons.Outlined.DarkMode,
                        contentDescription = null
                    )
                }
            )
//            SwitchPreference(
//                title = R.string.theme_settings_theme_title,
//                subtitle = R.string.theme_settings_theme_subtitle,
//                icon = {
//                    Icon(
//                        imageVector = Icons.Outlined.DarkMode,
//                        contentDescription = null
//                    )
//                },
//                isChecked = themeState.isDarkMode,
//                onCheckedChange = { themeViewModel.toggleTheme() }
//            )
            if (themeState.appTheme == 2 || (themeState.appTheme == 0 && isSystemInDarkTheme())) {
                SwitchPreference(
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
            }
            if (supportsDynamic()) {
                SwitchPreference(
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
        SettingsGroup(title = R.string.default_setting_section) {
            SwitchPreference(
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
            SwitchPreference(
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
        SettingsGroup(title = R.string.product_settings_product_section) {
            BasicPreference(
                title = R.string.product_settings_github_title,
                description = R.string.product_settings_github_subtitle,
                onClick = {
                    val intent = Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse(Constants.GITHUB_LINK)
                    )
                    context.startActivity(intent)
                },
                icon = ImageVector.vectorResource(R.drawable.github_icon)
            )
            BasicPreference(
                title = R.string.product_settings_privacy_title,
                description = R.string.product_settings_privacy_subtitle,
                onClick = {
                    val intent = Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse(Constants.PRIVACY_LINK)
                    )
                    context.startActivity(intent)
                },
                icon = Icons.Outlined.PrivacyTip
            )
            BasicPreference(
                title = R.string.product_settings_privacy_title,
                description = R.string.product_settings_privacy_subtitle,
                onClick = {
                    val intent = Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse(Constants.PRIVACY_LINK)
                    )
                    context.startActivity(intent)
                },
                icon = Icons.Outlined.PrivacyTip
            )
            BasicPreference(
                title = R.string.product_settings_privacy_title,
                description = R.string.product_settings_privacy_subtitle,
                onClick = {
                    val intent = Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse(Constants.PRIVACY_LINK)
                    )
                    context.startActivity(intent)
                },
                icon = Icons.Outlined.PrivacyTip
            )
        }
    }
}

@Preview(name = "SettingsScreen")
@Composable
private fun PreviewSettingsScreen() {
    SettingsScreen()
}
