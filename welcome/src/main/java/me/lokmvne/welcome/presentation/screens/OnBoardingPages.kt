package me.lokmvne.welcome.presentation.screens

import me.lokmvne.welcome.R

sealed class OnBoardingPages(
    val title: String,
    val description: String,
    val image: Int
) {
    data object Page1 : OnBoardingPages(
        title = "Welcome to PDF Toolbox",
        description = "Easily compress, split, and manage your PDF files. Our app offers quick and efficient tools to streamline your documents",
        image = R.drawable.onboarding1
    )

    data object Page2 : OnBoardingPages(
        title = "Effortless PDF Compression",
        description = "Reduce file size without losing quality. Save space and share PDFs faster with our high-quality compression feature",
        image = R.drawable.onboarding2
    )

    data object Page3 : OnBoardingPages(
        title = "Customize and Save PDFs",
        description = "Split large PDFs into smaller sections and save them instantly. Organize your documents the way you need them",
        image = R.drawable.onboarding3
    )
}