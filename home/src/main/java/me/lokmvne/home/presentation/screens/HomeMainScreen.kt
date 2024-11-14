package me.lokmvne.home.presentation.screens

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import me.lokmvne.common.ui.theme.displayFontFamily
import me.lokmvne.common.utils.ObserveAsEvent
import me.lokmvne.common.utils.navigationRoutes.WelcomeScreens
import me.lokmvne.home.R
import me.lokmvne.home.presentation.PdfNavGraph
import me.lokmvne.home.presentation.viewmodels.HomeMainScreenViewModel
import me.lokmvne.home.presentation.viewmodels.SignOutState


val permissionsToRequest1 = arrayOf(
    Manifest.permission.READ_EXTERNAL_STORAGE,
    Manifest.permission.WRITE_EXTERNAL_STORAGE,
)

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeMainScreen(navHostController: NavHostController) {
    val homeNavHostController = rememberNavController()
    val homeMainScreenViewModel = hiltViewModel<HomeMainScreenViewModel>()

    val requestSinglePermission = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
        }
    )

    val pdfAppRequestPermissions = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = { permissions ->
        }
    )
    ObserveAsEvent(
        flow = homeMainScreenViewModel.signOutEventChannelFlow,
        onEvent = {
            when (it) {
                is SignOutState.SignOutSuccess -> {
                    navHostController.navigate(WelcomeScreens.WelcomeLoginScreen.route) {
                        popUpTo(WelcomeScreens.WelcomeLoginScreen.route) {
                            inclusive = true
                        }
                    }
                }

                is SignOutState.SignOutFailure -> {

                }
            }
        }
    )

    LaunchedEffect(true) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            requestSinglePermission.launch(Manifest.permission.MANAGE_EXTERNAL_STORAGE)
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requestSinglePermission.launch(Manifest.permission.POST_NOTIFICATIONS)
        }

        pdfAppRequestPermissions.launch(permissionsToRequest1)
    }

    Scaffold(
        modifier = Modifier.windowInsetsPadding(WindowInsets.statusBars),
        topBar = {
            TopBar(onLogoutClicked = {
                homeMainScreenViewModel.googleFirebaseSignOutVM()
            })
        },
    ) { innerPadding ->
        Box(
            Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            PdfNavGraph(homeNavHostController)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(onLogoutClicked: () -> Unit) {
    TopAppBar(title = {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "EasyPDF", fontFamily = displayFontFamily
            )
        }
    }, modifier = Modifier.fillMaxWidth(), colors = TopAppBarDefaults.topAppBarColors(
        containerColor = MaterialTheme.colorScheme.background,
        navigationIconContentColor = MaterialTheme.colorScheme.onBackground,
        titleContentColor = MaterialTheme.colorScheme.onBackground
    ), navigationIcon = {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            IconButton(onClick = { onLogoutClicked() }) {
                Icon(
                    painter = painterResource(R.drawable.signout),
                    contentDescription = "Logout",
                    modifier = Modifier.rotate(180f)
                )
            }
        }
    })
}


fun Activity.openAppSettings() {
    Intent(
        Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
        Uri.fromParts("package", packageName, null)
    ).also(::startActivity)
}