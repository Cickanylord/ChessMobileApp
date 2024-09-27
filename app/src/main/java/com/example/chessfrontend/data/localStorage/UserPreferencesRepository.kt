package com.example.chessfrontend.data.localStorage

import com.example.chessfrontend.data.model.Credentials
import com.example.chessfrontend.data.model.Token
import kotlinx.coroutines.flow.Flow

interface UserPreferencesRepository {
    suspend fun storeCredentials(username: String, password: String, token: String)
    fun getCredentials(): Flow<Credentials>
    fun getToken(): Flow<Token>
    suspend fun isLoggedIn(): Boolean
}