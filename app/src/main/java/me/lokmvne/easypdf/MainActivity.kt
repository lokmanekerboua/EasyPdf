package me.lokmvne.easypdf

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.collectAsState
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import me.lokmvne.common.presentation.DataPreferencesViewModel
import me.lokmvne.common.ui.theme.EasyPDFTheme
import me.lokmvne.easypdf.presentation.navgraphs.MainNavGraph
import me.lokmvne.easypdf.presentation.screens.homescreens.HOME_MAIN_SCREEN_ROOTE
import me.lokmvne.easypdf.presentation.screens.homescreens.HomeScrs
import me.lokmvne.easypdf.presentation.screens.welcomescreens.GoogleFirebaseAuthViewModel
import me.lokmvne.easypdf.presentation.screens.welcomescreens.WELCOME_ROUTE
import me.lokmvne.easypdf.presentation.screens.welcomescreens.WelcomeScreens
import java.io.File
import javax.crypto.Cipher

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        enableEdgeToEdge()

        setContent {
            val dataPreferencesViewModel = hiltViewModel<DataPreferencesViewModel>()
            val googleFirebaseAuthViewModel = hiltViewModel<GoogleFirebaseAuthViewModel>()
            dataPreferencesViewModel.readOnboardingCompleted()
            val onBoardingCompleted = dataPreferencesViewModel.onboardingCompleted.collectAsState()
            val navController = rememberNavController()
            EasyPDFTheme {
                googleFirebaseAuthViewModel.checkIfGoogleFirebaseUserIsSignedInVM().let {
                    if (!it) {
                        onBoardingCompleted.value.let { onBoardingCompleted ->
                            if (onBoardingCompleted) {
                                MainNavGraph(
                                    navHostController = navController,
                                    context = this,
                                    startingGraph = WELCOME_ROUTE,
                                    startDestination = WelcomeScreens.WelcomeLoginScreen.route
                                )
                            } else {
                                MainNavGraph(
                                    navHostController = navController,
                                    context = this,
                                    startingGraph = WELCOME_ROUTE,
                                    startDestination = WelcomeScreens.WelcomeOnBoardingScreen.route
                                )
                            }
                        }
                    } else {
                        MainNavGraph(
                            navHostController = navController,
                            context = this,
                            startingGraph = HOME_MAIN_SCREEN_ROOTE,
                            startDestination = HomeScrs.HomeScreen.route
                        )
                    }
                }

            }
        }

    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        // Changing the theme doesn't recreate the activity, so set the E2E values again
        enableEdgeToEdge()
    }
}
