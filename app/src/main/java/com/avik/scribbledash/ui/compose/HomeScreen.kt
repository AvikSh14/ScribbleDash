package com.avik.scribbledash.ui.compose

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.avik.scribbledash.navigation.BottomNavDestination
import com.avik.scribbledash.navigation.BottomNavGraph
import com.avik.scribbledash.navigation.BottomNavigationBar

@Composable
fun HomeScreen() {
    Log.d("BottomNav", "Home Screen")
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = "Home Screen", textAlign = TextAlign.Center)
    }
}

@Composable
fun OtherScreen() {
    Log.d("BottomNav", "Other Screen")
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = "Other Screen", textAlign = TextAlign.Center)
    }
}

@Composable
fun MainScreen(navController: NavHostController) {
    Log.d("BottomNav", "Main Screen")
    Scaffold(
        bottomBar = { BottomNavigationBar(navController = navController) }
    ) { innerPadding ->

//        BottomNavGraph(navController = navController)
        NavHost(
            navController = navController,
            startDestination = BottomNavDestination.Home,
            modifier = Modifier.padding(innerPadding)
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
