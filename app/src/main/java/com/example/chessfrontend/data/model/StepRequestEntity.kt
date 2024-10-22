package com.example.chessfrontend.data.model

import kotlinx.serialization.Serializable

@Serializable
data class StepRequestEntity(
    val matchId: Long,
    val board: String
)
