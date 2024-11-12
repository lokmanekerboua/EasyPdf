package me.lokmvne.easypdf.presentation.homePresentation


import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import me.lokmvne.easypdf.AppScreens
import me.lokmvne.easypdf.presentation.homePresentation.screens.HomeMainScreen
import me.lokmvne.easypdf.presentation.homePresentation.screens.ProfileScreen

fun NavGraphBuilder.HomeNavGraph(
    navHostController: NavHostController,
) {
    navigation(
        route = AppScreens.HomeNavGraph.route,
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

const val HOME_PROFILE_SCREEN_ROUTE = "home_profile_screen"
const val HOME_MAIN_SCREEN_ROUTE = "home_main_screen"

sealed class HomeScrs(val route: String) {
    data object HomeMainScreen : HomeScrs(HOME_MAIN_SCREEN_ROUTE)
    data object ProfileScreen : HomeScrs(HOME_PROFILE_SCREEN_ROUTE)
}