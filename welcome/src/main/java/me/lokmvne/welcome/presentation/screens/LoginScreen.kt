package me.lokmvne.welcome.presentation.screens

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import me.lokmvne.common.utils.ObserveAsEvent
import me.lokmvne.common.utils.navigationRoutes.HOME_NAV_GRAPH_ROUTE
import me.lokmvne.common.utils.navigationRoutes.WelcomeScreens
import me.lokmvne.welcome.R
import me.lokmvne.welcome.presentation.viewmodels.LoginEvent
import me.lokmvne.welcome.presentation.viewmodels.LoginScreenViewModel

@Composable
fun WelcomeLoginScreen(
    navHostController: NavHostController, context: Context
) {
    val loginScreenViewModel = hiltViewModel<LoginScreenViewModel>()
    //val loginState = loginScreenViewModel.currentLoginState.collectAsState()

    ObserveAsEvent(
        flow = loginScreenViewModel.loginNavigationEventFlow,
        onEvent = {
            when (it) {
                is LoginEvent.NavigateToHomeScreen -> {
                    navHostController.navigate(HOME_NAV_GRAPH_ROUTE) {
                        popUpTo(WelcomeScreens.WelcomeLoginScreen.route) {
                            inclusive = true
                        }
                    }
                }
            }
        }
    )

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LoginScreenSkelton(googleFirebaseSignIn = {
            loginScreenViewModel.googleFirebaseSignInVM(
                context = context
            )
        })
    }
}


@Composable
fun LoginScreenSkelton(
    googleFirebaseSignIn: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .background(MaterialTheme.colorScheme.background)

        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    painter = painterResource(R.drawable.logo),
                    contentDescription = null,
                    modifier = Modifier.size(100.dp),
                    tint = MaterialTheme.colorScheme.onBackground
                )
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = "Login",
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground,
                    style = MaterialTheme.typography.headlineLarge
                )

            }
        }
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .clip(RoundedCornerShape(topEnd = 30.dp, topStart = 30.dp))
                .background(MaterialTheme.colorScheme.onBackground)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                LoginButton(
                    text = "Continue with Google",
                    icon = R.drawable.google,
                    description = "Google",
                    onclicked = {
                        googleFirebaseSignIn()
                    }
                )
                Spacer(modifier = Modifier.height(12.dp))
                LoginButton(
                    text = "Continue with Facebook",
                    icon = R.drawable.logosfacebook,
                    description = "Facebook",
                    onclicked = {

                    }
                )
                Spacer(modifier = Modifier.height(12.dp))
                LoginButton(
                    text = "Continue with Apple",
                    icon = R.drawable.applelogo,
                    description = "Apple",
                    onclicked = {

                    }
                )
            }
        }
    }
}

@Composable
fun LoginButton(
    text: String = "",
    icon: Int = R.drawable.logo,
    description: String = "",
    onclicked: () -> Unit = {}
) {
    OutlinedButton(
        modifier = Modifier
            .fillMaxWidth(0.75f)
            .padding(horizontal = 0.dp)
            .height(45.dp),
        onClick = { onclicked() },
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = MaterialTheme.colorScheme.background
        )
    ) {
        Row(
            //modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            //horizontalArrangement = Arrangement.SpaceAround
        ) {
            Box(
                modifier = Modifier.weight(1f)
            ) {
                Image(
                    painter = painterResource(icon),
                    contentDescription = description,
                    modifier = Modifier.size(24.dp)
                )
            }

            Box(
                modifier = Modifier.weight(3f)
            ) {
                Text(
                    text = text,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
