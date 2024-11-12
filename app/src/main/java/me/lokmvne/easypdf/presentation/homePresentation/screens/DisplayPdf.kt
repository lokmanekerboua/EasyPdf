package me.lokmvne.easypdf.presentation.homePresentation.screens

import android.graphics.Bitmap
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import coil.compose.AsyncImage

@Composable
fun DisplayPDF(pages: List<Bitmap>, modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = modifier
    ) {
        items(pages) { page ->
            AsyncImage(
                page,
                contentDescription = null,
                modifier = Modifier.aspectRatio(page.width.toFloat() / page.height.toFloat())
            )
        }
    }
}