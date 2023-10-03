package com.example.athenabus.presentation.main_screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.athenabus.R
import com.example.athenabus.presentation.common.MaterialBottomNavBar
import com.example.athenabus.presentation.common.MaterialTopAppBar
import com.example.athenabus.presentation.nvgraph.HomeNavGraph


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    navController: NavHostController = rememberNavController()
) {

    var selectedItemIndex by rememberSaveable {
        mutableIntStateOf(0)
    }
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    val snackbarHostState = remember { SnackbarHostState() }

    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        Scaffold(
            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
            topBar = {
                MaterialTopAppBar(
                    title = stringResource(id = R.string.app_name),
                    isHomeScreen = true,
                    canNavigateBack = navController.previousBackStackEntry != null,
                )
            },
            snackbarHost = {
                SnackbarHost(hostState = snackbarHostState)
            },
            bottomBar = { MaterialBottomNavBar(navController) },
//            floatingActionButtonPosition = FabPosition.Center,
//            floatingActionButton = {
//                Box {
//                    FloatingActionButton(
//                        shape = CircleShape,
//                        onClick = { navController.navigate(Route.SearchLineScreen.route) },
//                        modifier = Modifier
//                            .align(Alignment.Center)
//                            .offset(y = 54.dp)
//                            .size(64.dp),
//                        elevation = FloatingActionButtonDefaults.elevation(
//                            defaultElevation = 2.dp
//                        )
//                    ) {
//                        Icon(imageVector = Icons.Filled.Search, contentDescription = null, tint = MaterialTheme.colorScheme.surfaceTint)
//                    }
//                }
//            }
        ) {
            Box(modifier = Modifier.padding(it)) {
                HomeNavGraph(navController = navController)
            }
        }
    }
}

@Preview(name = "MainScreen")
@Composable
private fun PreviewMainScreen() {
    MainScreen(navController = rememberNavController())
}