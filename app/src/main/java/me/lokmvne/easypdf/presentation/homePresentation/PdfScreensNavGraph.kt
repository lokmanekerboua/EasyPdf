package me.lokmvne.easypdf.presentation.homePresentation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import me.lokmvne.easypdf.presentation.homePresentation.screens.CompressPdfScreen
import me.lokmvne.easypdf.presentation.homePresentation.screens.HomeScreen
import me.lokmvne.easypdf.presentation.homePresentation.screens.MergePdfScreen
import me.lokmvne.easypdf.presentation.homePresentation.screens.ProtectPdfScreen
import me.lokmvne.easypdf.presentation.homePresentation.screens.RotatePdfScreen
import me.lokmvne.easypdf.presentation.homePresentation.screens.SplitPdfScreen
import me.lokmvne.easypdf.presentation.homePresentation.screens.ToJpgPdfScreen
import me.lokmvne.easypdf.presentation.homePresentation.screens.WaterMarkPdfScreen

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

const val PDF_HOME_SCREEN_ROUTE = "PDF_home_main_screen"
const val PDF_COMPRESS_SCREEN_ROUTE = "home_compress_pdf_screen"
const val PDF_PROTECT_SCREEN_ROUTE = "home_encrypt_pdf_screen"
const val PDF_MERGE_SCREEN_ROUTE = "home_merge_pdf_screen"
const val PDF_SPLIT_SCREEN_ROUTE = "home_split_pdf_screen"
const val PDF_TO_JPG_SCREEN_ROUTE = "home_to_jpg_pdf_screen"
const val PDF_WATERMARK_SCREEN_ROUTE = "home_watermark_pdf_screen"
const val PDF_ROTATE_SCREEN_ROUTE = "home_rotate_pdf_screen"


sealed class PdfScreens(val route: String) {
    data object HomeScreen : PdfScreens(PDF_HOME_SCREEN_ROUTE)
    data object CompressPdfScreen : PdfScreens(PDF_COMPRESS_SCREEN_ROUTE)
    data object ProtectPdfScreen : PdfScreens(PDF_PROTECT_SCREEN_ROUTE)
    data object MergePdfScreen : PdfScreens(PDF_MERGE_SCREEN_ROUTE)
    data object SplitPdfScreen : PdfScreens(PDF_SPLIT_SCREEN_ROUTE)
    data object ToJpgPdfScreen : PdfScreens(PDF_TO_JPG_SCREEN_ROUTE)
    data object WatermarkPdfScreen : PdfScreens(PDF_WATERMARK_SCREEN_ROUTE)
    data object RotatePdfScreen : PdfScreens(PDF_ROTATE_SCREEN_ROUTE)
}