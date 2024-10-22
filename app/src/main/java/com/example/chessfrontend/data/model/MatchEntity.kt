package com.example.chessfrontend.data.model

import kotlinx.serialization.Serializable

@Serializable
data class MatchEntity(
    val id: Long,
    val challenger: Long,
    val challenged: Long,
    val board: String,
)
