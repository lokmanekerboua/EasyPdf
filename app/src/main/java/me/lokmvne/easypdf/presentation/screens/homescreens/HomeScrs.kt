package me.lokmvne.easypdf.presentation.screens.homescreens

const val HOME_GRAPH_ROUTE = "home_graph"
const val HOME_MAIN_SCREEN_ROOTE = "home_main_screen"
const val HOME_PROFILE_ROUTE = "profile"
const val HOME_HOME_ROUTE = "home"

sealed class HomeScrs(val route: String) {
    data object HomeScreen : HomeScrs(HOME_HOME_ROUTE)
    data object ProfileScreen : HomeScrs(HOME_PROFILE_ROUTE)
}