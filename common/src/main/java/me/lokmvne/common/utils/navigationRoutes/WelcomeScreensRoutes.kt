package me.lokmvne.common.utils.navigationRoutes

const val WELCOME_NAV_GRAPH_ROUTE = "welcomeNavGraph"
const val WELCOME_ONBOARDING_ROUTE = "welcomeonboarding"
const val WELCOME_LOGIN_ROUTE = "welcomelogin"

sealed class WelcomeScreens(val route: String) {
    data object WelcomeOnBoardingScreen : WelcomeScreens(WELCOME_ONBOARDING_ROUTE)
    data object WelcomeLoginScreen : WelcomeScreens(WELCOME_LOGIN_ROUTE)
}