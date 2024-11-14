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
import me.lokmvne.common.utils.navigationRoutes.AppMainScreens
import me.lokmvne.common.utils.navigationRoutes.WelcomeScreens
import me.lokmvne.home.presentation.HomeNavGraph
import me.lokmvne.welcome.presentation.WelcomeNavGraph

@Composable
fun MainNavGraph(
    navHostController: NavHostController,
    context: Context,
) {
    EasyPDFTheme {
        NavHost(
            navController = navHostController,
            startDestination = AppMainScreens.SplashScreen.route,
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {

            composable(AppMainScreens.SplashScreen.route) {
                SplashScreen(navHostController)
            }

            WelcomeNavGraph(context, navHostController)

            HomeNavGraph(navHostController)
        }
    }
}

