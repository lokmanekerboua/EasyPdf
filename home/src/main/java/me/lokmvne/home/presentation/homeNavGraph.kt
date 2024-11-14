package me.lokmvne.home.presentation


import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import me.lokmvne.common.utils.navigationRoutes.HOME_NAV_GRAPH_ROUTE
import me.lokmvne.common.utils.navigationRoutes.HomeScrs
import me.lokmvne.home.presentation.screens.HomeMainScreen
import me.lokmvne.home.presentation.screens.ProfileScreen

fun NavGraphBuilder.HomeNavGraph(
    navHostController: NavHostController,
) {
    navigation(
        route = HOME_NAV_GRAPH_ROUTE,
        startDestination = HomeScrs.HomeMainScreen.route,
    ) {
        composable(HomeScrs.HomeMainScreen.route) {
            HomeMainScreen(navHostController)
        }

        composable(HomeScrs.ProfileScreen.route) {
            ProfileScreen(navHostController)
        }
    }
}



