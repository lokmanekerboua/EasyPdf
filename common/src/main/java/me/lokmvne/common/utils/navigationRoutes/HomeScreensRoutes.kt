package me.lokmvne.common.utils.navigationRoutes


const val HOME_NAV_GRAPH_ROUTE = "home_NavGraph_screen"
const val HOME_PROFILE_SCREEN_ROUTE = "home_profile_screen"
const val HOME_MAIN_SCREEN_ROUTE = "home_main_screen"

sealed class HomeScrs(val route: String) {
    data object HomeMainScreen : HomeScrs(HOME_MAIN_SCREEN_ROUTE)
    data object ProfileScreen : HomeScrs(HOME_PROFILE_SCREEN_ROUTE)
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