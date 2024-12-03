package com.example.chessfrontend.data.localStorage

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.chessfrontend.data.model.Credentials
import com.example.chessfrontend.data.model.Token
import com.example.chessfrontend.data.model.UserEntity
import com.google.gson.Gson
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class UserPreferencesRepositoryImpl @Inject constructor(@ApplicationContext private val context: Context): LocalStorage {
    private val Context._dataStore: DataStore<Preferences> by preferencesDataStore("APP_PREFERENCES")

    override suspend fun storeCredentials(
        username: String,
        password: String,
        token: String
    ) {
        val dataStore: DataStore<Preferences> = context._dataStore
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.USERNAME] = username
            preferences[PreferencesKeys.PASSWORD] = password
            preferences[PreferencesKeys.TOKEN] = token
        }
    }

    override fun getCredentials(): Flow<Credentials> =
        context._dataStore.data.map { preferences ->
            Credentials(
                username = preferences[PreferencesKeys.USERNAME] ?: "",
                password = preferences[PreferencesKeys.PASSWORD] ?: ""
            )
        }

    override fun getToken(): Flow<Token> =
        context._dataStore.data.map { preferences ->
            Token(
                accessToken = preferences[PreferencesKeys.TOKEN] ?: ""
            )
        }

    override suspend fun refreshToken(token: Token) {
        val dataStore: DataStore<Preferences> = context._dataStore
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.TOKEN] = token.accessToken
        }
    }

    override suspend fun logout() {
        val dataStore: DataStore<Preferences> = context._dataStore
        dataStore.edit { preferences ->
            preferences.clear()
        }
    }

    override suspend fun saveOfflineGame(fen: String) {
        val dataStore: DataStore<Preferences> = context._dataStore
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.OFFLINE_GAME] = fen
        }
    }

    override fun getOfflineGame(): Flow<String> =
        context._dataStore.data.map { preferences ->
            preferences[PreferencesKeys.OFFLINE_GAME] ?: "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1"
        }

    override suspend fun deleteOfflineGame() {
        val dataStore: DataStore<Preferences> = context._dataStore
        dataStore.edit { preferences ->
            preferences.remove(PreferencesKeys.OFFLINE_GAME)
        }
    }

    override suspend fun saveAiGame(fen: String) {
        val dataStore: DataStore<Preferences> = context._dataStore
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.AI_GAME] = fen
        }
    }

    override fun getAiGame(): Flow<String> =
        context._dataStore.data.map { preferences ->
            preferences[PreferencesKeys.AI_GAME] ?: "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1"
        }

    override suspend fun deleteAiGame() {
        val dataStore: DataStore<Preferences> = context._dataStore
        dataStore.edit { preferences ->
            preferences.remove(PreferencesKeys.AI_GAME)
        }
    }

    suspend fun saveData() {
        val dataStore: DataStore<Preferences> = context._dataStore
        dataStore.edit { preferences ->
            preferences.remove(PreferencesKeys.AI_GAME)
        }
    }

    override suspend fun storeUser(profile: UserEntity) {
        val dataStore: DataStore<Preferences> = context._dataStore
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.User] = Gson().toJson(profile)
        }
    }

    override suspend fun getUser(): Flow<UserEntity?> =
        context
            ._dataStore.data
            .map { preferences ->
                preferences[PreferencesKeys.User]?.let {
                    return@map Gson().fromJson(it, UserEntity::class.java)
                }
            }

    override suspend fun saveLasOnlineGame(id: Long) {
        val dataStore: DataStore<Preferences> = context._dataStore
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.LAST_ONLINE_GAME] = id.toString()
        }
    }

    override suspend fun getLasOnlineGame(): Flow<Long> =
        context._dataStore.data.map { preferences ->
            preferences[PreferencesKeys.LAST_ONLINE_GAME]?.toLong() ?: -1L
        }


    object PreferencesKeys {
        val ID = stringPreferencesKey("id")
        val USERNAME = stringPreferencesKey("username")
        val PASSWORD = stringPreferencesKey("password")
        val TOKEN = stringPreferencesKey("token")
        val OFFLINE_GAME = stringPreferencesKey("offline_game")
        val AI_GAME = stringPreferencesKey("ai_game")
        val User = stringPreferencesKey("user")
        val LAST_ONLINE_GAME = stringPreferencesKey("last_online_game")
    }
}