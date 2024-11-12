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
                navHostController.navigate(AppScreens.WelcomeOnBoardingScreen.route) {
                    popUpTo(AppScreens.SplashScreen.route) {
                        inclusive = true
                    }
                }
            }

            StartDestination.WelcomeLoginScreen -> {
                navHostController.navigate(AppScreens.WelcomeLoginScreen.route) {
                    popUpTo(AppScreens.SplashScreen.route) {
                        inclusive = true
                    }
                }
            }

            StartDestination.HomeScreen -> {
                navHostController.navigate(AppScreens.HomeNavGraph.route) {
                    popUpTo(AppScreens.SplashScreen.route) {
                        inclusive = true
                    }
                }

            }
        }

    })
}