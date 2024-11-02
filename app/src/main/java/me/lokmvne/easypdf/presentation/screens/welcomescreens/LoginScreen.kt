package me.lokmvne.easypdf.presentation.screens.welcomescreens

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.credentials.CredentialManager
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import me.lokmvne.easypdf.R
import me.lokmvne.easypdf.presentation.screens.homescreens.HOME_HOME_ROUTE
import me.lokmvne.easypdf.presentation.screens.homescreens.HOME_MAIN_SCREEN_ROOTE

@Composable
fun WelcomeLoginScreen(
    navHostController: NavHostController, context: Context
) {
    val googleFirebaseAuthViewModel = hiltViewModel<GoogleFirebaseAuthViewModel>()
    val isSignInSuccessful =
        googleFirebaseAuthViewModel.isGoogleFirebaseSignInSuccessful.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LoginScreenSkelton(googleFirebaseSignIn = {
            googleFirebaseAuthViewModel.googleFirebaseSignInVM(
                context = context
            )
        })
    }
    isSignInSuccessful.value.let {
        if (it) {
            LaunchedEffect(true) {
                navHostController.navigate(HOME_MAIN_SCREEN_ROOTE) {
                    popUpTo(WELCOME_LOGIN_ROUTE) {
                        inclusive = true
                    }
                }
            }
        }
    }
}


@Composable
fun LoginScreenSkelton(
    googleFirebaseSignIn: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
            //.background(MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "PDFBit Logo",
        )

        Spacer(modifier = Modifier.height(25.dp))
        Text(
            text = "Welcome to PDFBit",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            fontStyle = FontStyle.Italic,
            color = MaterialTheme.colorScheme.onBackground
        )
        Spacer(modifier = Modifier.height(100.dp))

        OutlinedButton(
            modifier = Modifier.padding(horizontal = 20.dp),
            onClick = { googleFirebaseSignIn() },
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = MaterialTheme.colorScheme.onBackground
            )
        ) {
            Row(
                //modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Image(
                    painter = painterResource(id = R.drawable.google),
                    contentDescription = "Google Logo",
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = "Continue with Google", fontSize = 15.sp, fontWeight = FontWeight.Bold
                )
            }
        }
    }
}