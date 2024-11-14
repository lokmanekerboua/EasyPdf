package me.lokmvne.common.utils.navigationRoutes

const val SPLASH_SCREEN_ROUTE = "splashscreen"


sealed class AppMainScreens(val route: String) {
    data object SplashScreen : AppMainScreens(SPLASH_SCREEN_ROUTE)
}