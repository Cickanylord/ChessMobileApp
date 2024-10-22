package com.example.chessfrontend.ui.model

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.chessfrontend.data.model.MessageEntity
import java.time.LocalDateTime


data class MessageUiModel(
    val id: Long,
    val sender: Long,
    val receiver: Long,
    val text: String,
    val sentDate: LocalDateTime
)

fun MessageEntity.toUiModel() = MessageUiModel(
    id = id,
    sender = sender,
    receiver = receiver,
    text = text,
    sentDate = LocalDateTime.parse(sentDate)
)

