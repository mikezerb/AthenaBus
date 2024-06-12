package com.example.athenabus.presentation.favorites.tabs

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.athenabus.R
import com.example.athenabus.presentation.common.EmptyScreen
import com.example.athenabus.presentation.favorites.FavoriteScreenViewModel
import com.example.athenabus.presentation.favorites.components.FavoriteLineItem
import com.example.athenabus.presentation.navigation.Route
import com.example.athenabus.presentation.settings.SettingsViewModel

@Composable
fun FavoriteLineScreen(
    viewModel: FavoriteScreenViewModel = hiltViewModel(),
    navController: NavController = rememberNavController(),
    settingsViewModel: SettingsViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    val settingsState = settingsViewModel.settingState.value

    if (state.isLoading) {
        CircularProgressIndicator()
    } else if (state.favoriteLines.isEmpty()) {
        EmptyScreen(title = stringResource(R.string.no_favorite_lines_found))
    } else {
        Column(Modifier.fillMaxSize()) {
            LazyColumn {
                items(state.favoriteLines, key = { it.LineCode }) { line ->
                    FavoriteLineItem(
                        busLine = line,
                        onItemClick =
                        {
                            navController.navigate(
                                Route.LineDetailsActivity.route +
                                        "?lineId=${line.LineID}&lineCode=${line.LineCode}&lineDesc=${line.LineDescr}&isFav=${line.isFavorite}"
                            )
                        },
                        isEnglish = settingsState.lanCode != "el"
                    )
                }
            }
        }
    }

}

@Preview(name = "FavoriteLineScreen")
@Composable
private fun PreviewFavoriteLineScreen() {
    FavoriteLineScreen()
}