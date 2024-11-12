package me.lokmvne.home.data

import android.app.Service
import android.content.Intent
import android.os.IBinder
import dagger.hilt.android.AndroidEntryPoint
import me.lokmvne.home.repository.ApiRepository
import me.lokmvne.home.repository.ProcessingPdfRepository
import javax.inject.Inject

@AndroidEntryPoint
class ComporessService : Service() {

    @Inject
    lateinit var processingPdfRepository: ProcessingPdfRepository

    @Inject
    lateinit var apiRepository: ApiRepository

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return START_STICKY
    }
}