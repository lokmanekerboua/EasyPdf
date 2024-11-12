package me.lokmvne.home.data.remote

import me.lokmvne.home.data.models.request.processRequest
import me.lokmvne.home.data.models.response.ProcessResponse
import me.lokmvne.home.data.models.response.UploadFileResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface ProcessingPdf {

    @Multipart
    @POST("upload")
    suspend fun uploadpdf(
        @Header("Authorization") token: String,
        @Part("task") task: RequestBody,
        @Part file: MultipartBody.Part
    ): UploadFileResponse

    @POST("process")
    suspend fun processpdf(
        @Header("Authorization") token: String,
        @Body processRequest: processRequest
    ): ProcessResponse

    @GET("download/{task}")
    suspend fun downloadpdf(
        @Header("Authorization") token: String,
        @Path("task") task: String
    ): ResponseBody
}