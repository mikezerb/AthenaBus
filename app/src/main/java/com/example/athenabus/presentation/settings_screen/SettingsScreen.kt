package com.example.athenabus.presentation.settings_screen

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.CloudUpload
import androidx.compose.material.icons.twotone.DarkMode
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.athenabus.R
import com.example.athenabus.common.Constants
import com.example.athenabus.presentation.settings_screen.components.BasicPreference
import com.example.athenabus.presentation.settings_screen.components.SettingsGroup
import com.example.athenabus.presentation.settings_screen.components.SwitchPreference

@Composable
fun SettingsScreen(
    navController: NavController = rememberNavController(),
    viewModel: SettingsViewModel = hiltViewModel(),
    themeViewModel: ThemeViewModel = hiltViewModel()
) {
    // Observe the dark theme setting
    val darkThemeState by themeViewModel.themeState.collectAsState()
    var isOtherMode by remember { mutableStateOf(false) }

    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        SettingsGroup(title = R.string.theme_settings_theme_section) {
            SwitchPreference(
                title = R.string.theme_settings_theme_title,
                subtitle = R.string.theme_settings_theme_subtitle,
                icon = { Icon(imageVector = Icons.TwoTone.DarkMode, contentDescription = null) },
                isChecked = darkThemeState.isDarkMode,
                onCheckedChange = { themeViewModel.toggleTheme() }
            )
        }
        SettingsGroup(title = R.string.default_setting_section) {
            SwitchPreference(
                title = R.string.default_setting_title,
                subtitle = R.string.default_setting_desc,
                icon = { Icon(imageVector = Icons.TwoTone.CloudUpload, contentDescription = null) },
                isChecked = isOtherMode,
                onCheckedChange = { isOtherMode = !isOtherMode }
            )
        }
        SettingsGroup(title = R.string.theme_settings_product_section) {
            BasicPreference(
                title = R.string.theme_settings_github_title,
                description = R.string.theme_settings_github_subtitle,
                onClick = {
                    val intent = Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse(Constants.GITHUB_LINK)
                    )
                    context.startActivity(intent)
                },
                icon = ImageVector.vectorResource(R.drawable.github_icon)

            )
        }
    }
}


@Preview(name = "SettingsScreen")
@Composable
private fun PreviewSettingsScreen() {
    SettingsScreen()
}