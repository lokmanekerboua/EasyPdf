package me.lokmvne.easypdf

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import androidx.core.app.NotificationManagerCompat
import dagger.hilt.android.HiltAndroidApp
import me.lokmvne.home.utils.PdfOperationsNotificationManager
import javax.inject.Inject

@HiltAndroidApp
class EasyPDFApp : Application(){

    @Inject
    @PdfOperationsNotificationManager
    lateinit var notificationManager: NotificationManagerCompat

    override fun onCreate() {
        super.onCreate()

        val pdfCompressionChannel = NotificationChannel(
            "Pdf_Compression_Channel_Id",
            "Compression PDF Notification",
            NotificationManager.IMPORTANCE_HIGH
        )
        notificationManager.createNotificationChannel(pdfCompressionChannel)
    }
}