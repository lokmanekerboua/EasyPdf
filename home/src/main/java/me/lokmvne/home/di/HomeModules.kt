package me.lokmvne.home.di

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import me.lokmvne.home.data.remote.ApiClient
import me.lokmvne.home.utils.Constants.Companion.BASE_URL
import me.lokmvne.home.utils.PdfOperationsNotification
import me.lokmvne.home.utils.PdfOperationsNotificationManager
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HomeModules {

    @Provides
    @Singleton
    fun provideApiClient(): ApiClient {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiClient::class.java)
    }


    @Singleton
    @Provides
    @PdfOperationsNotification
    fun provideDirectReplyNotificationBuilder(
        @ApplicationContext context: Context
    ): NotificationCompat.Builder {
        return NotificationCompat.Builder(context, "Pdf_Compression_Channel_Id")
            //.setPriority(NotificationCompat.PRIORITY_HIGH)
            .setOnlyAlertOnce(false)
    }

    @Singleton
    @Provides
    @PdfOperationsNotificationManager
    fun providePdfOperationsNotificationManager(
        @ApplicationContext context: Context
    ): NotificationManagerCompat {
        val notificationManager = NotificationManagerCompat.from(context)
        return notificationManager
    }
}

sealed class PdfNotificationChannel(val id: String, val name: String) {
    data object PdfCompressionChannel :
        PdfNotificationChannel("Pdf_Compression_Channel_Id", "Compression PDF Notification")
}