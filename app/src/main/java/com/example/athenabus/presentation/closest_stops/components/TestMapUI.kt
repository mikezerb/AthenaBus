package com.example.athenabus.presentation.closest_stops.components

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun TestMapUI(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {

        var isMapLoaded by remember { mutableStateOf(false) }
        Surface (tonalElevation = 4.dp) {
            Row(
                modifier = Modifier
                    .fillMaxWidth().padding(horizontal = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Looasfsafasfasfasf")
                TextButton(
                    onClick = { }
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(imageVector = Icons.Default.Refresh, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = "Update Location", style = MaterialTheme.typography.bodySmall)
                    }
                }
            }
        }
        val scope = rememberCoroutineScope()
        SideEffect {
            scope.launch {
                delay(timeMillis = 2000)
                isMapLoaded = false
            }

        }
        Column(
            modifier = Modifier
                .background(Color.Black)
                .fillMaxWidth()
                .weight(1.2f)
        ) {
            Box(
                modifier = Modifier
                    .background(Color.Cyan)
                    .fillMaxSize(),
                contentAlignment = Alignment.TopCenter
            ) {
                Text(text = "GOOGLE MAPS")
            }
            if (!isMapLoaded) {
                AnimatedVisibility(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(0.5f),
                    visible = !isMapLoaded,
                    enter = EnterTransition.None,
                    exit = fadeOut()
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .background(MaterialTheme.colorScheme.background)
                            .wrapContentSize(),
                        color = MaterialTheme.colorScheme.surfaceTint
                    )
                }
            }
        }


        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.8f)
        ) {
            Column {
                Text(text = "agasga")
                Text(text = "agasga")
                Text(text = "agasga")
            }
        }

    }
}

@Preview(name = "TestMapUI", showBackground = true)
@Preview(name = "TestMapUI", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun PreviewTestMapUI() {
    TestMapUI(modifier = Modifier.fillMaxSize())
}