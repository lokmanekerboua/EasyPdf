package me.lokmvne.easypdf

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import me.lokmvne.common.utils.ObserveAsEvent
import me.lokmvne.common.utils.navigationRoutes.AppMainScreens
import me.lokmvne.common.utils.navigationRoutes.HOME_NAV_GRAPH_ROUTE
import me.lokmvne.common.utils.navigationRoutes.WelcomeScreens

@Composable
fun SplashScreen(navHostController: NavHostController) {
    val splashScreenViewModel = hiltViewModel<SplashScreenViewModel>()
    splashScreenViewModel.readOnboardingCompleted()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    )

    ObserveAsEvent(flow = splashScreenViewModel.startDestinationChannelFlow, onEvent = {
        when (it) {

            StartDestination.WelcomeOnBoardingScreen -> {
                navHostController.navigate(WelcomeScreens.WelcomeOnBoardingScreen.route) {
                    popUpTo(AppMainScreens.SplashScreen.route) {
                        inclusive = true
                    }
                }
            }

            StartDestination.WelcomeLoginScreen -> {
                navHostController.navigate(WelcomeScreens.WelcomeLoginScreen.route) {
                    popUpTo(AppMainScreens.SplashScreen.route) {
                        inclusive = true
                    }
                }
            }

            StartDestination.HomeScreen -> {
                navHostController.navigate(HOME_NAV_GRAPH_ROUTE) {
                    popUpTo(AppMainScreens.SplashScreen.route) {
                        inclusive = true
                    }
                }

            }
        }

    })
}