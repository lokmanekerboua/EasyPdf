package me.lokmvne.home.presentation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import me.lokmvne.common.utils.navigationRoutes.PdfScreens
import me.lokmvne.home.presentation.screens.HomeScreen
import me.lokmvne.home.presentation.screens.opScreens.CompressPdfScreen
import me.lokmvne.home.presentation.screens.opScreens.MergePdfScreen
import me.lokmvne.home.presentation.screens.opScreens.ProtectPdfScreen
import me.lokmvne.home.presentation.screens.opScreens.RotatePdfScreen
import me.lokmvne.home.presentation.screens.opScreens.SplitPdfScreen
import me.lokmvne.home.presentation.screens.opScreens.ToJpgPdfScreen
import me.lokmvne.home.presentation.screens.opScreens.WaterMarkPdfScreen

@Composable
fun PdfNavGraph(
    navHostController: NavHostController
) {
    NavHost(
        navHostController,
        startDestination = PdfScreens.HomeScreen.route
    ) {

        composable(PdfScreens.HomeScreen.route) {
            HomeScreen(navHostController)
        }
        composable(PdfScreens.CompressPdfScreen.route) {
            CompressPdfScreen(navHostController)
        }
        composable(PdfScreens.ProtectPdfScreen.route) {
            ProtectPdfScreen(navHostController)
        }

        composable(PdfScreens.MergePdfScreen.route) {
            MergePdfScreen(navHostController)
        }
        composable(PdfScreens.SplitPdfScreen.route) {
            SplitPdfScreen(navHostController)
        }
        composable(PdfScreens.ToJpgPdfScreen.route) {
            ToJpgPdfScreen(navHostController)
        }
        composable(PdfScreens.WatermarkPdfScreen.route) {
            WaterMarkPdfScreen(navHostController)
        }

        composable(PdfScreens.RotatePdfScreen.route) {
            RotatePdfScreen(navHostController)
        }
    }
}

