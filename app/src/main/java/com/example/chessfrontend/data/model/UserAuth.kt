package com.example.chessfrontend.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class UserAuth(
    @SerialName("userName") val userName: String,
    @SerialName("password") val password: String
)
