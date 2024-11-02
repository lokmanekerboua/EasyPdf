package me.lokmvne.easypdf.presentation.navgraphs

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import me.lokmvne.easypdf.presentation.screens.homescreens.HOME_GRAPH_ROUTE
import me.lokmvne.easypdf.presentation.screens.homescreens.HomeScreen
import me.lokmvne.easypdf.presentation.screens.homescreens.HomeScrs
import me.lokmvne.easypdf.presentation.screens.homescreens.ProfileScreen

@Composable
fun HomeNavGraph(
    navHostController: NavHostController,
) {
    NavHost(
        navController = navHostController,
        startDestination = HomeScrs.HomeScreen.route,
    ) {
        composable(HomeScrs.HomeScreen.route) {
            HomeScreen(navHostController)
        }
        composable(HomeScrs.ProfileScreen.route) {
            ProfileScreen(navHostController)
        }
    }
}