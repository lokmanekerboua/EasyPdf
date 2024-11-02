package me.lokmvne.home.data.models.request

data class processRequest(
    val files: List<File>,
    val task: String,
    val tool: String
)