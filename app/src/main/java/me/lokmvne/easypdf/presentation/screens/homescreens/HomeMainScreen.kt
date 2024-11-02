package me.lokmvne.easypdf.presentation.screens.homescreens

import android.annotation.SuppressLint
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import me.lokmvne.common.ui.theme.displayFontFamily
import me.lokmvne.easypdf.R
import me.lokmvne.easypdf.presentation.navgraphs.HomeNavGraph
import me.lokmvne.easypdf.presentation.screens.welcomescreens.GoogleFirebaseAuthViewModel
import me.lokmvne.easypdf.presentation.screens.welcomescreens.WelcomeScreens
import kotlin.math.min
import kotlin.random.Random

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeMainScreen(navHostController: NavHostController) {
    val homeNavHostController = rememberNavController()
    val googleFirebaseAuthViewModel = hiltViewModel<GoogleFirebaseAuthViewModel>()
    val isSignedOut = googleFirebaseAuthViewModel.isGoogleFirebaseSignOutSuccessful.collectAsState()
    val scope = rememberCoroutineScope()

    val snackbarHostState by remember { mutableStateOf(SnackbarHostState()) }

    val density = LocalDensity.current
    val dotBackground = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.1f)
    Scaffold(
        modifier = Modifier.windowInsetsPadding(WindowInsets.statusBars),
        topBar = {
            TopBar(onLogoutClicked = {
                googleFirebaseAuthViewModel.googleFirebaseSignOutVM()
            })
        }, snackbarHost = {
            SnackbarHost(snackbarHostState)
        }, floatingActionButton = {
            MyFloatingActionButton(onclick = {
                scope.launch {
                    snackbarHostState.showSnackbar(
                        "Add PDF clicked"
                    )
                }
            })
        }
    ) { innerPadding ->
        BoxWithConstraints(
            Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            with(density) {
                val maxWidth = maxWidth
                val maxHeight = maxHeight

                for (i in 0..50) {
                    var state by remember { mutableStateOf(false) }

                    LaunchedEffect(Unit) {
                        delay(Random.nextLong(300, 5000))
                        state = true
                    }

                    val animScale by animateFloatAsState(
                        targetValue = if (state) 1f else .75f,
                        animationSpec = tween(
                            durationMillis = 12000,
                            easing = LinearEasing
                        )
                    )

                    val animCenterX by animateFloatAsState(
                        targetValue = if (state) .8f else 1f,
                        animationSpec = tween(
                            durationMillis = 9000,
                            easing = FastOutSlowInEasing
                        )
                    )

                    val animCenterY by animateFloatAsState(
                        targetValue = if (state) .8f else 1f,
                        animationSpec = tween(
                            durationMillis = 9000,
                            easing = FastOutSlowInEasing
                        )
                    )

                    val centerX = remember {
                        Random.nextInt(0, maxWidth.toPx().toInt()).toFloat()
                    }
                    val centerY = remember {
                        Random.nextInt(0, maxHeight.toPx().toInt()).toFloat()
                    }
                    val radius = remember {
                        Random.nextInt(16, min(maxWidth.toPx(), minHeight.toPx()).toInt() / 14)
                            .toFloat()
                    }
                    val alpha = remember { (Random.nextInt(10, 85) / 100f) }

                    Canvas(modifier = Modifier.fillMaxSize()) {
                        drawCircle(
                            color = dotBackground,
                            center = Offset(
                                x = if (i % 2 != 0) centerX * animCenterX else centerX,
                                y = if (i % 2 == 0) centerY * animCenterY else centerY
                            ),
                            radius = radius * animScale,
                            alpha = alpha
                        )
                    }
                }
            }


            HomeNavGraph(
                homeNavHostController,
            )
        }
        LaunchedEffect(isSignedOut.value) {
            isSignedOut.value.let {
                if (it) {
                    navHostController.navigate(WelcomeScreens.WelcomeLoginScreen.route) {
                        popUpTo(HomeScrs.HomeScreen.route) {
                            inclusive = true
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun MyFloatingActionButton(
    onclick: () -> Unit
) {
    FloatingActionButton(onClick = { onclick() }) {
        Icon(
            imageVector = Icons.Default.Add, contentDescription = "Add PDF"
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    onLogoutClicked: () -> Unit
) {
    TopAppBar(title = {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(R.drawable.bluesky),
                contentDescription = "EasyPDF",
            )
            Spacer(modifier = Modifier.width(8.dp))
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