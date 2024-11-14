package me.lokmvne.welcome.presentation

import android.content.Context
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import me.lokmvne.common.utils.navigationRoutes.WELCOME_NAV_GRAPH_ROUTE
import me.lokmvne.common.utils.navigationRoutes.WelcomeScreens
import me.lokmvne.welcome.presentation.screens.OnBoardingScreen
import me.lokmvne.welcome.presentation.screens.WelcomeLoginScreen


fun NavGraphBuilder.WelcomeNavGraph(
    context: Context,
    navHostController: NavHostController,
    startDestination: WelcomeScreens = WelcomeScreens.WelcomeLoginScreen
) {
    navigation(
        startDestination = startDestination.route,
        route = WELCOME_NAV_GRAPH_ROUTE
    ) {

        composable(WelcomeScreens.WelcomeOnBoardingScreen.route) {
            OnBoardingScreen(
                navHostController
            )
        }

        composable(WelcomeScreens.WelcomeLoginScreen.route) {
            WelcomeLoginScreen(
                navHostController, context
            )
        }
    }
}