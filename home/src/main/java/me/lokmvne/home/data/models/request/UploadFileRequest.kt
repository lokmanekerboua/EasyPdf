package me.lokmvne.home.data.models.request

import java.io.File

data class UploadFileRequest(
    val task: String,
    val cloud_file: String
)
