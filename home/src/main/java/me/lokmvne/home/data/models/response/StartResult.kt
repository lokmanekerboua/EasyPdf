package me.lokmvne.home.data.models.response

data class StartResult(
    val server: String,
    val task: String,
    val remaining_files: Int
)