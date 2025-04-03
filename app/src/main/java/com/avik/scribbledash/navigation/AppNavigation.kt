package com.avik.scribbledash.navigation

import android.util.Log
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.avik.scribbledash.R
import com.avik.scribbledash.ui.compose.HomeScreen
import com.avik.scribbledash.ui.compose.OtherScreen
import kotlinx.serialization.Serializable

sealed interface BottomNavDestination {
    @Serializable
    data object Home : BottomNavDestination

    @Serializable
    data object Other : BottomNavDestination
}

data class BottomNavRoute(
    @StringRes val name: Int,
    val route: BottomNavDestination,
    @DrawableRes val icon: Int
) {
    @Composable
    fun title() = stringResource(id = name)

    @Composable
    fun icon() = painterResource(id = icon)
}

val bottomNavRoutes = listOf(
    BottomNavRoute(
        R.string.route_other,
        BottomNavDestination.Other,
        R.drawable.other_bottom_nav_item
    ),
    BottomNavRoute(
        R.string.route_home,
        BottomNavDestination.Home,
        R.drawable.home_bottom_nav_icon
    )
)

@Composable
fun BottomNavigationBar(navController: NavController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    NavigationBar {
        bottomNavRoutes.forEach { bottomNavRoute ->
            NavigationBarItem(
                icon = {
                    Icon(
                        painter = bottomNavRoute.icon(),
                        contentDescription = bottomNavRoute.title()
                    )
                },
                selected = currentDestination?.hierarchy?.any { it.hasRoute(bottomNavRoute.route::class) } == true,
                label = { Text(text = bottomNavRoute.title()) },
                onClick = {
                    navController.navigate(bottomNavRoute.route) {
                        navController.graph.startDestinationRoute?.let { startDestinationRoute ->
                            popUpTo(startDestinationRoute) {
                                saveState = true
                            }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}

@Composable
fun BottomNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = BottomNavDestination.Home
    ) {
        composable<BottomNavDestination.Home> {
            HomeScreen()
        }
        composable<BottomNavDestination.Other> {
            OtherScreen()
        }
    }
}
