package com.example.chessfrontend.data.model

import kotlinx.serialization.Serializable

@Serializable
data class UserPost(
    val name: String,
    val password: String
) {

}
