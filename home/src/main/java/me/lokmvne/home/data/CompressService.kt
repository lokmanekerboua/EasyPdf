package me.lokmvne.home.data

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Environment
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import me.lokmvne.common.utils.Resource
import me.lokmvne.home.R
import me.lokmvne.home.data.models.request.AuthPdf
import me.lokmvne.home.data.models.request.File
import me.lokmvne.home.data.models.request.processRequest
import me.lokmvne.home.data.remote.ProcessingPdf
import me.lokmvne.home.repository.ApiRepository
import me.lokmvne.home.repository.ProcessingPdfRepository
import me.lokmvne.home.utils.Constants.Companion.PUBLIC_KEY
import me.lokmvne.home.utils.PdfOperationsNotification
import me.lokmvne.home.utils.PdfOperationsNotificationManager
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

@AndroidEntryPoint
class CompressService : Service() {
    val context = this

    // Define a scope for coroutines in the service
    private val serviceScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    @Inject
    lateinit var processingPdfRepository: ProcessingPdfRepository

    @Inject
    lateinit var apiRepository: ApiRepository

    @Inject
    @PdfOperationsNotification
    lateinit var operationsNotificationBuilder: NotificationCompat.Builder

    @Inject
    @PdfOperationsNotificationManager
    lateinit var notificationManager: NotificationManagerCompat


    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            PdfOperationsActions.START.toString() -> {
                val filePath = intent.getStringExtra("filePath")
                if (filePath != null) {
                    start(filePath)
                } else {
                    stopSelf()
                }
            }

            PdfOperationsActions.STOP.toString() -> {
                serviceScope.cancel()
                stopSelf()
            }
        }
        return super.onStartCommand(intent, flags, startId)
    }

    private fun start(filePath: String) {

        val stopIntent = Intent(context, CompressReceiver::class.java)
        val stopPendingIntent = PendingIntent.getBroadcast(
            context,
            0,
            stopIntent,
            PendingIntent.FLAG_MUTABLE
        )

        val stopAction = NotificationCompat.Action.Builder(
            0,
            "Stop",
            stopPendingIntent,
        ).build()

        val notification = operationsNotificationBuilder
            .setSmallIcon(R.drawable.compress)
            .setContentTitle("Compressing")
            .setContentText("Compressing PDF...")
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .clearActions()
            .addAction(stopAction)
            .build()

        startForeground(1, notification)

        serviceScope.launch {
            startCompressing(filePath)
        }
    }

    @SuppressLint("MissingPermission")
    private suspend fun startCompressing(filePath: String) {
//-----------------------------------------------authenticate----------------------------------------------------
        val tokenres = apiRepository.authenticate(AuthPdf(PUBLIC_KEY))
        when (tokenres) {

            is Resource.Error -> {
            }

            is Resource.Loading -> {}
            is Resource.Success -> {
//--------------------------------------------------start---------------------------------------------------------
                val res = apiRepository.Start(COMPRESS_TOOL, tokenres.data!!.token)
                when (res) {
                    is Resource.Error -> {
                        serviceScope.cancel()
                        stopSelf()
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
                            filePathToUpload = filePath,
                            processingPdfApi
                        )
                        when (uploadRes) {
                            is Resource.Error -> {
                                Log.e("UploadError", uploadRes.message.toString())
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
                                            }

                                            is Resource.Loading -> {}
                                            is Resource.Success -> {
                                                downloadRes.data?.let {
                                                    saveFile(context, downloadRes.data!!)
                                                    stopSelf()
                                                    val endNotif = operationsNotificationBuilder
                                                        .setSmallIcon(R.drawable.compress)
                                                        .setContentTitle("Compressing")
                                                        .setContentText("Compression complete")
                                                        .setPriority(NotificationCompat.PRIORITY_MAX)
                                                        .clearActions()
                                                        .build()
                                                    notificationManager.notify(
                                                        1,
                                                        endNotif
                                                    )

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

    enum class PdfOperationsActions {
        START, STOP
    }
}

const val COMPRESS_TOOL = "compress"