package com.example.chessfrontend.data.model

import kotlinx.serialization.Serializable

@Serializable
data class MatchEntity(
    val id: Long,
    val challenger: Long = -1L,
    val challenged: Long = -1L,
    val board: String,
    val isGoing: Boolean = true,
    val winner: Long? = null,
    val loser: Long? = null,
)
