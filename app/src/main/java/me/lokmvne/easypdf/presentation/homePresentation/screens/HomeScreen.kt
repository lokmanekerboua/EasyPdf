package me.lokmvne.easypdf.presentation.homePresentation.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import me.lokmvne.easypdf.R
import me.lokmvne.easypdf.presentation.homePresentation.PdfScreens

@Composable
fun HomeScreen(
    navHostController: NavHostController,
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.fillMaxSize()
    ) {
        items(screenList) { screen ->
            AppMainScreenItem(screen, navHostController)
        }
    }
}


@Composable
fun AppMainScreenItem(screenItem: HomeScreenItem, navcontroller: NavHostController) {
    Card(
        modifier = Modifier
            .height(180.dp)
            .padding(12.dp)
            .shadow(
                elevation = 12.dp,
                shape = RoundedCornerShape(8.dp),
                clip = true,
                ambientColor = screenItem.color,
                spotColor = screenItem.color
            )
            .clickable {
                navcontroller.navigate(screenItem.route)
            },
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(8.dp),
        border = BorderStroke(1.dp, screenItem.color),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background,
        )
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                painter = painterResource(id = screenItem.icon),
                contentDescription = null,
                modifier = Modifier.size(45.dp),
                tint = screenItem.color
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = screenItem.title,
                color = screenItem.color
            )
        }
    }
}

val screenList = listOf<HomeScreenItem>(
    HomeScreenItem(
        title = "Compress PDF",
        description = "Compress PDF files",
        color = Color(0xFF4d7c0f),
        route = PdfScreens.CompressPdfScreen.route,
        icon = R.drawable.compress
    ),
    HomeScreenItem(
        title = "Protect PDF",
        description = "Protect PDF files with password",
        color = Color(0xFFbe123c),
        route = PdfScreens.ProtectPdfScreen.route,
        icon = R.drawable.protect
    ),
    HomeScreenItem(
        title = "Merge PDF",
        description = "Merge multiple PDF files",
        color = Color(0xFF6d28d9),
        route = PdfScreens.MergePdfScreen.route,
        icon = R.drawable.merge
    ),
    HomeScreenItem(
        title = "Split PDF",
        description = "Split PDF files",
        color = Color(0xFFd97706),
        route = PdfScreens.SplitPdfScreen.route,
        icon = R.drawable.split
    ),
    HomeScreenItem(
        title = "PDF to JPG",
        description = "Convert PDF to JPG",
        color = Color(0xFF00BFA5),
        route = PdfScreens.ToJpgPdfScreen.route,
        icon = R.drawable.tojpg
    ),
    HomeScreenItem(
        title = "Watermark PDF",
        description = "Add watermark to PDF",
        color = Color(0xFF1d4ed8),
        route = PdfScreens.WatermarkPdfScreen.route,
        icon = R.drawable.watermark
    ),
    HomeScreenItem(
        title = "Rotate PDF",
        description = "Rotate PDF files",
        color = Color(0xFFd946ef),
        route = PdfScreens.RotatePdfScreen.route,
        icon = R.drawable.rotate
    ),
)

data class HomeScreenItem(
    val title: String,
    val description: String,
    val color: Color,
    val route: String,
    val icon: Int
)
