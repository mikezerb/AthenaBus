package com.example.athenabus.presentation.main_screen.components

import android.content.Context
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SignalWifiConnectedNoInternet4
import androidx.compose.material.icons.filled.Wifi
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.athenabus.R
import com.example.athenabus.data.network.ConnectionStatus
import com.example.athenabus.data.network.NetworkConnectivityObserver
import kotlinx.coroutines.delay

@Composable
fun ConnectivityStatus(
    context: Context
) {
    val connectivityObserver = NetworkConnectivityObserver(context)
    val networkStatus by connectivityObserver.observe()
        .collectAsState(initial = ConnectionStatus.Unavailable)

    val isConnected = networkStatus === ConnectionStatus.Available

    var visibility by remember { mutableStateOf(false) }

    LaunchedEffect(isConnected) {
        if (!isConnected) {
            delay(700)
        }
        visibility = !isConnected
    }

    AnimatedVisibility(
        visible = visibility,
        enter = expandVertically(),
        exit = shrinkVertically()
    ) {
        ConnectivityStatusBox(isConnected = isConnected)
    }
}

@Composable
fun ConnectivityStatusBox(isConnected: Boolean) {
    val backgroundColor by animateColorAsState(
        if (isConnected) MaterialTheme.colorScheme.background
        else MaterialTheme.colorScheme.errorContainer,
        label = "errorBackgroundColor"
    )
    val textColor by animateColorAsState(
        if (isConnected) MaterialTheme.colorScheme.onBackground
        else MaterialTheme.colorScheme.error,
        label = "errorTextColor"
    )

    val message = if (isConnected)
        stringResource(R.string.back_online)
    else
        stringResource(R.string.no_internet_connection)

    val iconResource = if (isConnected) {
        Icons.Default.Wifi
    } else {
        Icons.Default.SignalWifiConnectedNoInternet4
    }

    Box(
        modifier = Modifier
            .background(backgroundColor)
            .fillMaxWidth()
            .padding(4.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(imageVector = iconResource, contentDescription = null)
            Spacer(modifier = Modifier.size(8.dp))
            Text(message, color = textColor, style = MaterialTheme.typography.bodyMedium)
        }
    }
}