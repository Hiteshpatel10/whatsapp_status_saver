package com.geekaid.whatsappstatussaver.model

data class Status(
    val path: String,
    val type: StatusType
)

enum class StatusType{
    IMAGE,
    VIDEO
}