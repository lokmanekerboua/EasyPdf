package me.lokmvne.easypdf

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import me.lokmvne.common.ui.theme.EasyPDFTheme
import me.lokmvne.easypdf.presentation.homePresentation.HomeNavGraph
import me.lokmvne.easypdf.presentation.welcomePresentation.screens.OnBoardingScreen
import me.lokmvne.easypdf.presentation.welcomePresentation.screens.WelcomeLoginScreen

@Composable
fun MainNavGraph(
    navHostController: NavHostController,
    context: Context,
) {
    EasyPDFTheme {
        NavHost(
            navController = navHostController,
            startDestination = AppScreens.SplashScreen.route,
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {

            composable(AppScreens.SplashScreen.route) {
                SplashScreen(navHostController)
            }

            composable(AppScreens.WelcomeOnBoardingScreen.route) {
                OnBoardingScreen(
                    navHostController
                )
            }

            composable(AppScreens.WelcomeLoginScreen.route) {
                WelcomeLoginScreen(
                    navHostController, context
                )
            }
            HomeNavGraph(navHostController)
        }
    }
}

const val SPLASH_SCREEN_ROUTE = "splashscreen"
const val WELCOME_ONBOARDING_ROUTE = "welcomeonboarding"
const val WELCOME_LOGIN_ROUTE = "welcomelogin"
const val HOME_NAV_GRAPH_ROUTE = "home_NavGraph_screen"


sealed class AppScreens(val route: String) {
    data object SplashScreen : AppScreens(SPLASH_SCREEN_ROUTE)
    data object WelcomeOnBoardingScreen : AppScreens(WELCOME_ONBOARDING_ROUTE)
    data object WelcomeLoginScreen : AppScreens(WELCOME_LOGIN_ROUTE)
    data object HomeNavGraph : AppScreens(HOME_NAV_GRAPH_ROUTE)
}