package com.example.athenabus.presentation.about_screen

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BugReport
import androidx.compose.material.icons.filled.SentimentSatisfiedAlt
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.athenabus.presentation.about_screen.components.AboutItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutScreen(
    modifier: Modifier = Modifier,
    navController: NavController = rememberNavController()
) {
    val context = LocalContext.current
    Surface(
        modifier.fillMaxSize(),
        tonalElevation = 4.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AboutItem(
                title = "About Creator",
                subtitle = "Info about the me",
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
            HorizontalDivider()
            AboutItem(
                title = "Found a Bug?",
                subtitle = "Feel free to open an issue about any bugs or requests",
                icon = Icons.Default.BugReport,
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
        }

    }


}
