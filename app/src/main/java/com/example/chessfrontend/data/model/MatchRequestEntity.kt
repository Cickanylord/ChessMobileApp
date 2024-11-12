package com.example.chessfrontend.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MatchRequestEntity (
    @SerialName("challenged")val challenged: Long,
    @SerialName("board")val board: String
)


