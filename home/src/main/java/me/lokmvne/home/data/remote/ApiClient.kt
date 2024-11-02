package me.lokmvne.home.data.remote

import me.lokmvne.home.data.models.request.AuthPdf
import me.lokmvne.home.data.models.response.AuthPdfResponse
import me.lokmvne.home.data.models.response.StartResult
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiClient {

    @Headers(
        "Content-Type: application/json",
        "Connection: keep-alive",
        "Authorization: Bearer 1234567890",
        "Set-Cookie: _csrf=HTN-X0QhHFHYCuQWteWrnzCyFS9wrOCw; path=/; HttpOnly; SameSite=Lax",
        "Transfer-Encoding: chunked"
    )
    @POST("auth")
    suspend fun Authenticate(@Body authPdf: AuthPdf): AuthPdfResponse


    @GET("start/{tool}")
    suspend fun Start(
        @Header("Authorization") token: String,
        @Path("tool") tool: String
    ): StartResult
}