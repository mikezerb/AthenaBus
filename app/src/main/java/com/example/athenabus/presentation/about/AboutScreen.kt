package com.example.athenabus.presentation.about

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SentimentSatisfiedAlt
import androidx.compose.material.icons.outlined.BugReport
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.twotone.DirectionsBus
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.core.content.pm.PackageInfoCompat
import com.example.athenabus.R
import com.example.athenabus.presentation.about.components.AboutItem


data class AppVersion(
    val versionName: String,
    val versionNumber: Long,
)

fun getAppVersion(
    context: Context,
): AppVersion? {
    return try {
        val packageManager = context.packageManager
        val packageName = context.packageName
        val packageInfo = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            packageManager.getPackageInfo(packageName, PackageManager.PackageInfoFlags.of(0))
        } else {
            packageManager.getPackageInfo(packageName, 0)
        }
        AppVersion(
            versionName = packageInfo.versionName,
            versionNumber = PackageInfoCompat.getLongVersionCode(packageInfo),
        )
    } catch (e: Exception) {
        null
    }
}

@Composable
fun AboutScreen(
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues
) {
    val context = LocalContext.current
    Surface(
        modifier
            .fillMaxSize()
            .padding(paddingValues),
        tonalElevation = 2.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 32.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.TwoTone.DirectionsBus,
                    contentDescription = null,
                    modifier = Modifier.padding(8.dp),
                    tint = MaterialTheme.colorScheme.surfaceTint
                )
                Text(
                    text = stringResource(id = R.string.app_name),
                    style = MaterialTheme.typography.headlineMedium,
                    fontFamily = FontFamily(Font(R.font.poppins_light))
                )
            }
            HorizontalDivider()
            AboutItem(
                title = "About Creator",
                subtitle = "Info about me",
                icon = Icons.Default.SentimentSatisfiedAlt,
                button = {
                    TextButton(onClick = {
                        Toast.makeText(
                            context, "About mikezerb", Toast.LENGTH_SHORT
                        ).show()
                    }) {
                        Text(text = "View Profile")
                    }
                })
            AboutItem(
                title = "Found a Bug?",
                subtitle = "Feel free to open an issue about any bugs or requests",
                icon = Icons.Outlined.BugReport,
                button = {
                    TextButton(onClick = {
                        Toast.makeText(
                            context, "Report bug", Toast.LENGTH_SHORT
                        ).show()
                    }) {
                        Text(text = "Open issue")
                    }
                }
            )
            AboutItem(
                title = "Version",
                subtitle = getAppVersion(context = context)?.versionName.toString(),
                icon = Icons.Outlined.Info,
            )
        }

    }


}
