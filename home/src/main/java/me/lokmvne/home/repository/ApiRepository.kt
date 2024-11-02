package me.lokmvne.home.repository

import me.lokmvne.common.utils.Resource
import me.lokmvne.home.data.models.request.AuthPdf
import me.lokmvne.home.data.models.response.AuthPdfResponse
import me.lokmvne.home.data.models.response.StartResult
import me.lokmvne.home.data.remote.ApiClient
import javax.inject.Inject

class ApiRepository @Inject constructor(
    private val apiClient: ApiClient
) {
    suspend fun authenticate(authPdf: AuthPdf): Resource<AuthPdfResponse> {
        try {
            val response = apiClient.Authenticate(authPdf)
            return Resource.Success(response)
        } catch (e: Exception) {
            return Resource.Error(e.message.toString())
        }
    }

    suspend fun Start(tool: String, token: String): Resource<StartResult> {
        try {
            val response = apiClient.Start(
                token = "Bearer $token",
                tool
            )
            return Resource.Success(response)
        } catch (e: Exception) {
            return Resource.Error(e.message.toString())
        }
    }
}