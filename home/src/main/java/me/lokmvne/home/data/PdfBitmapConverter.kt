package me.lokmvne.home.data

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.pdf.PdfRenderer
import android.net.Uri
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject


class PdfBitmapConverter @Inject constructor(
    @ApplicationContext private val context: Context
) {
    var renderer: PdfRenderer? = null

    suspend fun pdfToBitMap(contentUri: Uri): List<Bitmap> {
        return withContext(Dispatchers.IO) {
            renderer?.close()
            val pdfDescriptor = context.contentResolver.openFileDescriptor(contentUri, "r")
            pdfDescriptor.use { descriptor ->
                if (descriptor != null) {
                    with(PdfRenderer(descriptor)) {
                        renderer = this
                        return@withContext (0 until pageCount).map { index ->
                            openPage(index).use { page ->
                                val bitmap = Bitmap.createBitmap(
                                    page.width, page.height, Bitmap.Config.ARGB_8888
                                )

                                val canvas = Canvas(bitmap).apply {
                                    drawColor(Color.WHITE)
                                    drawBitmap(bitmap, 0f, 0f, null)
                                }

                                page.render(
                                    bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY
                                )
                                bitmap
                            }
                        }
                    }
                }
                return@withContext emptyList<Bitmap>()
            }
        }
    }

    suspend fun closeRenderer() {
        withContext(Dispatchers.IO) {
            renderer?.close()
        }
    }
}