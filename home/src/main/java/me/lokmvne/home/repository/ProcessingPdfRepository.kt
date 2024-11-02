package me.lokmvne.home.repository

import android.util.Log
import me.lokmvne.common.utils.Resource
import me.lokmvne.home.data.models.request.processRequest
import me.lokmvne.home.data.models.response.ProcessResponse
import me.lokmvne.home.data.models.response.UploadFileResponse
import me.lokmvne.home.data.remote.ProcessingPdf
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.ResponseBody
import java.io.File
import javax.inject.Inject

class ProcessingPdfRepository @Inject constructor() {
    suspend fun uploadpdf(
        token: String,
        task: String,
        filePathToUpload: String,
        processingPdfApi: ProcessingPdf
    ): Resource<UploadFileResponse> {
        try {
            val filePdf = File(filePathToUpload)

            val fileRequestBody: RequestBody =
                filePdf.asRequestBody(filePathToUpload.toMediaTypeOrNull())

            val filePart = MultipartBody.Part.createFormData("file", filePdf.name, fileRequestBody)

            val ttask = task.toRequestBody("text/plain".toMediaTypeOrNull())

            val uploadingRes =
                processingPdfApi.uploadpdf(token = "Bearer $token", task = ttask, file = filePart)

            return Resource.Success(uploadingRes)

        } catch (e: Exception) {
            return Resource.Error("repo: " + e.message.toString())
        }
    }

    suspend fun processpdf(
        token: String,
        processRequest: processRequest,
        procesingPdfApi: ProcessingPdf
    ): Resource<ProcessResponse> {
        try {
            val processingRes = procesingPdfApi.processpdf("Bearer $token", processRequest)
            return Resource.Success(processingRes)
        } catch (e: Exception) {
            return Resource.Error(e.message.toString())
        }
    }

    suspend fun downloadpdf(
        token: String,
        task: String,
        procesingPdfApi: ProcessingPdf
    ): Resource<ResponseBody> {
        try {
            val downloadRes = procesingPdfApi.downloadpdf("Bearer $token", task)
            return Resource.Success(downloadRes)
        } catch (e: Exception) {
            return Resource.Error(e.message.toString())
        }
    }
}