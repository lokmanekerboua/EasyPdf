package me.lokmvne.easypdf.presentation.homePresentation.viewmodels

import android.content.Context
import android.os.Environment
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import me.lokmvne.common.utils.Resource
import me.lokmvne.home.data.models.request.AuthPdf
import me.lokmvne.home.data.models.request.File
import me.lokmvne.home.data.models.request.processRequest
import me.lokmvne.home.data.remote.ProcessingPdf
import me.lokmvne.home.repository.ApiRepository
import me.lokmvne.home.repository.ProcessingPdfRepository
import me.lokmvne.home.utils.Constants.Companion.PUBLIC_KEY
import okhttp3.ResponseBody
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.time.Instant
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor() : ViewModel() {

}