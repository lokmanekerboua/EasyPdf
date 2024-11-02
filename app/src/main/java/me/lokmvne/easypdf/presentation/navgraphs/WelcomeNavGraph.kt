package me.lokmvne.easypdf.presentation.navgraphs

import android.content.Context
import androidx.credentials.CredentialManager
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import me.lokmvne.easypdf.presentation.screens.welcomescreens.OnBoardingScreen
import me.lokmvne.easypdf.presentation.screens.welcomescreens.WELCOME_LOGIN_ROUTE
import me.lokmvne.easypdf.presentation.screens.welcomescreens.WELCOME_ONBOARDING_ROUTE
import me.lokmvne.easypdf.presentation.screens.welcomescreens.WELCOME_ROUTE
import me.lokmvne.easypdf.presentation.screens.welcomescreens.WelcomeLoginScreen
import me.lokmvne.easypdf.presentation.screens.welcomescreens.WelcomeScreens

fun NavGraphBuilder.WelcomeNavGraph(
    welcomestartDestination: String,
    context: Context,
    navHostController: NavHostController
) {
    navigation(
        startDestination = welcomestartDestination,
        route = WELCOME_ROUTE
    ) {
        composable(WelcomeScreens.WelcomeLoginScreen.route) {
            WelcomeLoginScreen(
                navHostController, context
            )
        }

        composable(WelcomeScreens.WelcomeOnBoardingScreen.route) {
            OnBoardingScreen(
                navHostController
            )
        }
    }
}