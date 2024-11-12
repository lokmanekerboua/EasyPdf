package me.lokmvne.easypdf.presentation.homePresentation.viewmodels

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
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
import me.lokmvne.home.data.PdfBitmapConverter
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
class CompressPdfViewModel @Inject constructor(
    private val processingPdfRepository: ProcessingPdfRepository,
    private val apiRepository: ApiRepository,
    private val pdfBitmapConverter: PdfBitmapConverter,
    @ApplicationContext private val context: Context
) : ViewModel() {
    private val _rendererPages = MutableStateFlow<List<Bitmap>>(emptyList())
    val rendererPages = _rendererPages.asStateFlow()

    fun pdfToBitmap(uri: Uri) {
        viewModelScope.launch {
            _rendererPages.value = pdfBitmapConverter.pdfToBitMap(uri)
        }
    }

//---------------------------------------------------------------------------------------------------

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    val filePath = mutableStateOf<String?>(null)


    fun Start() {
        _isLoading.value = true
        viewModelScope.launch {
//-----------------------------------------------authenticate----------------------------------------------------
            val tokenres = apiRepository.authenticate(AuthPdf(PUBLIC_KEY))
            when (tokenres) {

                is Resource.Error -> {
                    Toast.makeText(context, "Auth Error", Toast.LENGTH_SHORT).show()
                    _isLoading.value = false
                }

                is Resource.Loading -> {}
                is Resource.Success -> {
//--------------------------------------------------start---------------------------------------------------------
                    val res = apiRepository.Start(COMPRESS_TOOL, tokenres.data!!.token)
                    when (res) {
                        is Resource.Error -> {
                            Toast.makeText(context, "Start Error", Toast.LENGTH_SHORT).show()
                            _isLoading.value = false
                        }

                        is Resource.Loading -> {}
                        is Resource.Success -> {
                            val baseUrl = "https://" + "${res.data!!.server}/v1/"
                            val processingPdfApi =
                                createRetrofitInstance(baseUrl).create(
                                    ProcessingPdf::class.java
                                )
//--------------------------------------------------UploadPdf---------------------------------------------------------
                            val uploadRes = processingPdfRepository.uploadpdf(
                                token = tokenres.data!!.token,
                                task = res.data!!.task,
                                filePathToUpload = filePath.value!!,
                                processingPdfApi
                            )
                            when (uploadRes) {
                                is Resource.Error -> {
                                    Log.e("UploadError", uploadRes.message.toString())
                                    _isLoading.value = false
                                    Toast.makeText(context, "Upload Error", Toast.LENGTH_SHORT)
                                        .show()
                                }

                                is Resource.Loading -> {}
                                is Resource.Success -> {
//--------------------------------------------------processPDF---------------------------------------------------------
                                    val processRes = processingPdfRepository.processpdf(
                                        token = tokenres.data!!.token,
                                        processRequest = processRequest(
                                            files = listOf(
                                                File(
                                                    uploadRes.data!!.server_filename,
                                                    "myfile.pdf"
                                                )
                                            ),
                                            task = res.data!!.task,
                                            tool = COMPRESS_TOOL

                                        ),
                                        processingPdfApi
                                    )
                                    when (processRes) {
                                        is Resource.Error -> {
                                            _isLoading.value = false
                                            Toast.makeText(
                                                context,
                                                "Process Error",
                                                Toast.LENGTH_SHORT
                                            )
                                                .show()
                                        }

                                        is Resource.Loading -> {}
                                        is Resource.Success -> {
//--------------------------------------------------download PDF---------------------------------------------------------
                                            val downloadRes = processingPdfRepository.downloadpdf(
                                                token = tokenres.data!!.token,
                                                task = res.data!!.task,
                                                processingPdfApi
                                            )
                                            when (downloadRes) {
                                                is Resource.Error -> {
                                                    _isLoading.value = false
                                                    Toast.makeText(
                                                        context,
                                                        "Download Error",
                                                        Toast.LENGTH_SHORT
                                                    ).show()
                                                }

                                                is Resource.Loading -> {}
                                                is Resource.Success -> {
                                                    _isLoading.value = false
                                                    downloadRes.data?.let {
                                                        saveFile(context, downloadRes.data!!)
                                                        Toast.makeText(
                                                            context,
                                                            "Compressed PDF downloaded successfully",
                                                            Toast.LENGTH_SHORT
                                                        ).show()
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun saveFile(context: Context, body: ResponseBody) {
        val downloadsDir =
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
        val filename = Date.from(Instant.now()).time.toString()
        val file = java.io.File(downloadsDir, "${filename}.pdf")

        var inputStream: InputStream? = null
        var outputStream: OutputStream? = null
        try {
            inputStream = body.byteStream()
            outputStream = FileOutputStream(file)
            val buffer = ByteArray(4096)
            var bytesRead: Int
            while (inputStream.read(buffer).also { bytesRead = it } != -1) {
                outputStream.write(buffer, 0, bytesRead)
            }
            outputStream.flush()
            println("File saved successfully in Downloads")
        } catch (e: IOException) {
            e.printStackTrace()
            println("Error saving file: ${e.message}")
        } finally {
            inputStream?.close()
            outputStream?.close()
        }
    }

    private fun createRetrofitInstance(baseUrl: String): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}

const val COMPRESS_TOOL = "compress"