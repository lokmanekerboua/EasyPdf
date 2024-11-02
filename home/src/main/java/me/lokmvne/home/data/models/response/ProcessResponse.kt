package me.lokmvne.home.data.models.response

data class ProcessResponse(
    val download_filename: String,
    val filesize: Int,
    val output_extensions: String,
    val output_filenumber: Int,
    val output_filesize: Int,
    val status: String,
    val timer: String
)