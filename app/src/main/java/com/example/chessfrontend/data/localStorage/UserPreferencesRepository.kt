package com.example.chessfrontend.data.localStorage

import com.example.chessfrontend.data.model.Credentials
import com.example.chessfrontend.data.model.Token
import com.example.chessfrontend.data.model.UserEntity
import kotlinx.coroutines.flow.Flow

interface UserPreferencesRepository {
    /** user information crud **/
    suspend fun storeCredentials(username: String, password: String, token: String)
    fun getCredentials(): Flow<Credentials>
    fun getToken(): Flow<Token>
    suspend fun refreshToken(token: Token)
    /** deletes use information **/
    suspend fun logout()

    /** offline multiplayer crud **/
    suspend fun saveOfflineGame(fen: String)
    fun getOfflineGame(): Flow<String>
    suspend fun deleteOfflineGame()

    /** ai game crud **/
    suspend fun saveAiGame(fen: String)
    fun getAiGame(): Flow<String>
    suspend fun deleteAiGame()

    /** save user id **/
    suspend fun storeUserId(profile: UserEntity)

    /** get user id **/
    suspend fun getUserId(): Flow<Long>
}