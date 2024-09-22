package com.example.chessfrontend.data

import android.content.Context

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.chessfrontend.data.model.Token
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

data class Credentials(val username: String, val password: String)

object DataStore {
    private val Context._dataStore: DataStore<Preferences> by preferencesDataStore("APP_PREFERENCES")


    suspend fun storeCredentials(
        context: Context,
        username: String,
        password: String,
        token: String
    ) {
        val dataStore : DataStore<Preferences> = context._dataStore
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.USERNAME] = username
            preferences[PreferencesKeys.PASSWORD] = password
            preferences[PreferencesKeys.TOKEN] = token
        }
    }

    fun getCredentials(context: Context): Flow<Credentials> =
        context._dataStore.data.map { preferences ->
        Credentials(
            username = preferences[PreferencesKeys.USERNAME] ?: "",
            password = preferences[PreferencesKeys.PASSWORD] ?: ""
        )
    }

    fun getToken(context: Context): Flow<Token> =
        context._dataStore.data.map { preferences ->
            Token(
                accessToken = preferences[PreferencesKeys.TOKEN] ?: ""
            )
        }



    suspend fun isLoggedIn(context: Context): Boolean {
        return getToken(context).first().accessToken != ""
    }

    object PreferencesKeys {
        val USERNAME = stringPreferencesKey("username")
        val PASSWORD = stringPreferencesKey("password")
        val TOKEN = stringPreferencesKey("token")
    }
}