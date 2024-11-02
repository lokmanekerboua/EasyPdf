package me.lokmvne.easypdf.presentation.screens.welcomescreens

const val WELCOME_ROUTE = "welcome"
const val WELCOME_LOGIN_ROUTE = "welcomelogin"
const val WELCOME_ONBOARDING_ROUTE = "welcomeonboarding"

sealed class WelcomeScreens(val route: String) {
    data object WelcomeOnBoardingScreen : WelcomeScreens(WELCOME_ONBOARDING_ROUTE)
    data object WelcomeLoginScreen : WelcomeScreens(WELCOME_LOGIN_ROUTE)
}