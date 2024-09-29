package com.example.chessfrontend.data.model

import kotlinx.serialization.Serializable

@Serializable
class MessageEntity(
    val id: Long,
    val sender: Long,
    val receiver: Long,
    val text: String,
    val sentDate: String
)