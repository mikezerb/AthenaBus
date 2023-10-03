package com.example.athenabus.presentation.common

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.example.athenabus.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MaterialTopAppBar(
    title: String,
    subtitle: String? = null,
    isHomeScreen: Boolean = false,
    isLineDetailsScreen: Boolean = false,
    canNavigateBack: Boolean = false,
    navigateUp: () -> Unit = { },
    onSettingsClick: () -> Unit = { },
    // TODO: Add click for map, favorite or schedule alarm buttons
) {
    val navController = rememberNavController()
    TopAppBar(
        title = {
            if (isHomeScreen) {
                Text(
                    text = title,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    fontFamily = FontFamily(Font(R.font.poppins_semibold))
                )
            } else if (isLineDetailsScreen) {
                Column {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleLarge
//                        style = TextStyle(
//                            fontFamily = FontFamily.Default,
//                            fontWeight = FontWeight.Bold,
//                            fontSize = 20.sp,
//                            letterSpacing = 0.15.sp
//                        )
                    )
                    if (!subtitle.isNullOrBlank()) {
                        Text(
                            modifier = Modifier.padding(start = 4.dp),
                            text = subtitle,
                            style = TextStyle(
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Normal,
                                letterSpacing = 0.25.sp,
                                color = MaterialTheme.colorScheme.secondary
                            )
                        )
                    }
                }
            } else {
                Text(
                    text = title,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Start
                )
            }
        },
        navigationIcon = {
            if (isLineDetailsScreen || navController.previousBackStackEntry != null) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            }
        },
        actions = {
//            if (isHomeScreen) {
//                IconButton(onClick = onSettingsClick) {
//                    Icon(
//                        imageVector = Icons.Outlined.Settings,
//                        contentDescription = "Settings"
//                    )
//                }
//            }
        },
        modifier = Modifier.fillMaxWidth()
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun HomeScreenTopAppBarPreview() {
    MaterialTopAppBar(
        title = "AthenaBus",
        isHomeScreen = true
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun LineDetailsScreenTopAppBarPreview() {
    MaterialTopAppBar(
        title = "608",
        subtitle = "1231",
        isLineDetailsScreen = true,
    )

}

