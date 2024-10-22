package com.example.chessfrontend.data.model

import kotlinx.serialization.Serializable

@Serializable
data class MessageOutEntity(
    val receiverId:Long,
    val text: String

)
