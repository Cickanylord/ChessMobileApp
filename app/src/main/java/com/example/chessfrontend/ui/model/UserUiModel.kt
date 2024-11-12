package com.example.chessfrontend.ui.model

import com.example.chessfrontend.data.model.UserEntity

data class UserUiModel(
    val id: Long = -1,
    val name: String = "",
    val roles: List<String> = emptyList(),
    val messagesSent: List<Long> = emptyList(),
    val messagesReceived: List<Long> = emptyList(),
    val challenger: List<Long> = emptyList(),
    val challenged: List<Long> = emptyList(),
    val friendList: List<Long> = emptyList(),
    val winedMatches: List<Long> = emptyList(),
    val lostMatches: List<Long> = emptyList()
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
    winedMatches = matchesWined,
    lostMatches = matchesLost
)
