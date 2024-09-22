package com.example.chessfrontend.data.model

import androidx.core.app.NotificationCompat.MessagingStyle.Message
import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: Long,
    val name: String,
    val roles: List<String>,
    val messagesSent: List<Long>,
    val messagesReceived: List<Long>,
    val challenger: List<Long>,
    val challenged: List<Long>,
    val friendList: List<Long>,
) {

}
