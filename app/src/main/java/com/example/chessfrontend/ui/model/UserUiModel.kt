package com.example.chessfrontend.ui.model

import com.example.chessfrontend.data.model.UserEntity

data class UserUiModel(
    val id: Long,
    val name: String,
    val roles: List<String>,
    val messagesSent: List<Long>,
    val messagesReceived: List<Long>,
    val challenger: List<Long>,
    val challenged: List<Long>,
    val friendList: List<Long>,
)

fun UserEntity.toUiModel() = UserUiModel(
    id = id,
    name = name,
    roles = roles,
    messagesSent = messagesSent,
    messagesReceived = messagesReceived,
    challenger = challenger,
    challenged = challenged,
    friendList = friendList,
)
