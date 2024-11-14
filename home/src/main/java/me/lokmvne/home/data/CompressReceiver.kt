package me.lokmvne.home.data

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import dagger.hilt.android.AndroidEntryPoint
import me.lokmvne.home.utils.PdfOperationsNotification
import me.lokmvne.home.utils.PdfOperationsNotificationManager
import javax.inject.Inject

@AndroidEntryPoint
class CompressReceiver : BroadcastReceiver() {
    val context = this

    @Inject
    @PdfOperationsNotification
    lateinit var operationsNotificationBuilder: NotificationCompat.Builder

    @Inject
    @PdfOperationsNotificationManager
    lateinit var notificationManager: NotificationManagerCompat

    override fun onReceive(context: Context?, intent: Intent?) {
        Intent(context, CompressService::class.java).also {
            it.action = CompressService.PdfOperationsActions.STOP.toString()
            context?.startService(it)
        }
        notificationManager.cancel(1)
    }
}