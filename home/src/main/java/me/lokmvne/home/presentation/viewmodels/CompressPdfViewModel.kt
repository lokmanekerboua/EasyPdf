package me.lokmvne.home.presentation.viewmodels

import android.graphics.Bitmap
import android.net.Uri
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import me.lokmvne.home.data.PdfBitmapConverter
import javax.inject.Inject

@HiltViewModel
class CompressPdfViewModel @Inject constructor(
    private val pdfBitmapConverter: PdfBitmapConverter,
) : ViewModel() {
    val filePath = mutableStateOf<String?>(null)

    private val _rendererPages = MutableStateFlow<List<Bitmap>>(emptyList())
    val rendererPages = _rendererPages.asStateFlow()

    fun pdfToBitmap(uri: Uri) {
        viewModelScope.launch {
            _rendererPages.value = pdfBitmapConverter.pdfToBitMap(uri)
        }
    }

    fun closeRenderer() {
        viewModelScope.launch {
            _rendererPages.value = emptyList()
        }
    }
}