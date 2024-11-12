package me.lokmvne.easypdf.presentation.welcomePresentation.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import me.lokmvne.common.ui.theme.EasyPDFTheme
import me.lokmvne.easypdf.AppScreens
import me.lokmvne.easypdf.presentation.welcomePresentation.viewmodels.OnBoardingScreenViewModel

@Composable
fun OnBoardingScreen(
    navHostController: NavHostController,
) {
    val pages = listOf(
        OnBoardingPages.Page1,
        OnBoardingPages.Page2,
        OnBoardingPages.Page3
    )
    val onBoardingScreenViewModel = hiltViewModel<OnBoardingScreenViewModel>()

    val pagerState = rememberPagerState(0, pageCount = { pages.size })

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.Center),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            OnBoardingPage(
                page = pages[it]
            )
        }

        AnimatedVisibility(
            pagerState.currentPage == pages.size - 1,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .windowInsetsPadding(WindowInsets.navigationBars)
                .padding(bottom = 16.dp)
        ) {
            GetStaredButton(
                modifier = Modifier
                    .padding(horizontal = 100.dp)
            ) {
                onBoardingScreenViewModel.setOnboardingCompleted()
                navHostController.navigate(AppScreens.WelcomeLoginScreen.route) {
                    popUpTo(AppScreens.WelcomeOnBoardingScreen.route) {
                        inclusive = true
                    }
                }
            }
        }

    }

}


@Composable
fun OnBoardingPage(page: OnBoardingPages) {
    Column(
        modifier = Modifier.fillMaxSize().padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = page.image),
            contentDescription = page.title,
            modifier = Modifier
                .height(300.dp)
                .fillMaxWidth(),
            contentScale = ContentScale.Inside
        )
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = page.title,
            fontStyle = FontStyle.Normal,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = page.description,
            fontSize = 18.sp,
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun OnBoardingPagePreview() {
    EasyPDFTheme {
        OnBoardingPage(page = OnBoardingPages.Page1)
    }
}

@Composable
fun GetStaredButton(modifier: Modifier = Modifier, onclick: () -> Unit) {
    Button(
        modifier = modifier,
        onClick = { onclick() },
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.tertiary,
            contentColor = MaterialTheme.colorScheme.onTertiary
        )
    ) {
        Text(text = "Get Started")
    }
}