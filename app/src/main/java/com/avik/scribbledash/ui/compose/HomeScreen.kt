package com.avik.scribbledash.ui.compose

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.avik.scribbledash.R
import com.avik.scribbledash.navigation.BottomNavDestination
import com.avik.scribbledash.navigation.BottomNavigationBar
import com.avik.scribbledash.navigation.HomeNavDestination
import com.avik.scribbledash.ui.theme.TextColorBrown
import com.avik.scribbledash.ui.theme.TextColorDarkBrown

@Composable
fun HomeMainScreen(onNavigateToGameMode: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 32.dp, start = 16.dp, end = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.start_drawing),
            style = MaterialTheme.typography.labelLarge,
            color = TextColorDarkBrown
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            modifier = Modifier.height(24.dp),
            text = stringResource(R.string.select_game_mode),
            style = MaterialTheme.typography.bodyMedium,
            color = TextColorBrown
        )

        Spacer(modifier = Modifier.height(8.dp))

        Image(
            painter = painterResource(id = R.drawable.mode),
            contentDescription = stringResource(R.string.select_game_mode),
            modifier = Modifier.clickable {
                onNavigateToGameMode()
            }
        )
    }
}

@Composable
fun GameModeSelectionScreen(onNavigateToDraw: () -> Unit) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = "Game Mode Selection Screen", textAlign = TextAlign.Center)
    }
}

@Composable
fun OtherScreen() {
    Log.d("BottomNav", "Other Screen")
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = "Other Screen", textAlign = TextAlign.Center)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavHostController) {
    Log.d("BottomNav", "Main Screen")
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.app_name),
                        style = MaterialTheme.typography.titleLarge,
                        color = TextColorDarkBrown
                    )
                }
            )
        },
        bottomBar = { BottomNavigationBar(navController = navController) }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = BottomNavDestination.Home,
            modifier = Modifier.padding(innerPadding),
        ) {
            composable<BottomNavDestination.Home> {
                HomeScreen()
            }
            composable<BottomNavDestination.Other> {
                OtherScreen()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {
    val homeNavController = rememberNavController()

    NavHost(
        navController = homeNavController,
        startDestination = HomeNavDestination.HomeMain,
        route = BottomNavDestination.Home::class
    ) {
        composable<HomeNavDestination.HomeMain> {
            HomeMainScreen(onNavigateToGameMode = { homeNavController.navigate(HomeNavDestination.GameModeSelection) })
        }
        composable<HomeNavDestination.GameModeSelection> {
            GameModeSelectionScreen(onNavigateToDraw = { homeNavController.navigate(HomeNavDestination.DrawScreen) })
        }
        composable<HomeNavDestination.DrawScreen> {
            // DrawScreen()
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun HomeScreenPreview() {
    HomeMainScreen(onNavigateToGameMode = {})
}
