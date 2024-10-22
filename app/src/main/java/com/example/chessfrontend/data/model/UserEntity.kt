package com.example.chessfrontend.data.model

import kotlinx.serialization.Serializable

@Serializable
data class UserEntity(
    val id: Long,
    val name: String,
    val roles: List<String>,
    val messagesSent: List<Long>,
    val messagesReceived: List<Long>,
    val challenger: List<Long>,
    val challenged: List<Long>,
    val friendList: List<Long>,
)