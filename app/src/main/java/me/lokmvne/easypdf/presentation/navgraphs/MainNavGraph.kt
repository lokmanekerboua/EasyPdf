package me.lokmvne.easypdf.presentation.navgraphs

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
import me.lokmvne.easypdf.presentation.screens.homescreens.HOME_MAIN_SCREEN_ROOTE
import me.lokmvne.easypdf.presentation.screens.homescreens.HomeMainScreen

@Composable
fun MainNavGraph(
    navHostController: NavHostController,
    context: Context,
    startingGraph: String,
    startDestination: String
) {
    EasyPDFTheme {
        NavHost(
            navController = navHostController,
            startDestination = startingGraph,
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            WelcomeNavGraph(
                welcomestartDestination = startDestination,
                navHostController = navHostController,
                context = context,
            )

            composable(HOME_MAIN_SCREEN_ROOTE) {
                HomeMainScreen(navHostController)
            }
        }
    }
}