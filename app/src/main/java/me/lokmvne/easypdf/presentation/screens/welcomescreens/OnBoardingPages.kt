package me.lokmvne.easypdf.presentation.screens.welcomescreens

import me.lokmvne.easypdf.R

sealed class OnBoardingPages(
    val title: String,
    val description: String,
    val image: Int
) {
    data object Page1 : OnBoardingPages(
        title = "Welcome to the app",
        description = "This is a description of the app",
        image = R.drawable.onboarding1
    )

    data object Page2 : OnBoardingPages(
        title = "Another page",
        description = "This is another description",
        image = R.drawable.onboarding2
    )

    data object Page3 : OnBoardingPages(
        title = "Last page",
        description = "This is the last description",
        image = R.drawable.onboarding3
    )
}