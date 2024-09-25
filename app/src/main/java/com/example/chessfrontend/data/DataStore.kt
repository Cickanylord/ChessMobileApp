package com.example.chessfrontend.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.chessfrontend.data.model.Credentials
import com.example.chessfrontend.data.model.Token
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map


class DataStoreService(@ApplicationContext private val context: Context) {
    private val Context._dataStore: DataStore<Preferences> by preferencesDataStore("APP_PREFERENCES")


    suspend fun storeCredentials(
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

    fun getCredentials(): Flow<Credentials> =
        context._dataStore.data.map { preferences ->
        Credentials(
            username = preferences[PreferencesKeys.USERNAME] ?: "",
            password = preferences[PreferencesKeys.PASSWORD] ?: ""
        )
    }

    fun getToken(): Flow<Token> =
        context._dataStore.data.map { preferences ->
            Token(
                accessToken = preferences[PreferencesKeys.TOKEN] ?: ""
            )
        }



    suspend fun isLoggedIn(): Boolean {
        return getToken().first().accessToken != ""
    }

    object PreferencesKeys {
        val USERNAME = stringPreferencesKey("username")
        val PASSWORD = stringPreferencesKey("password")
        val TOKEN = stringPreferencesKey("token")
    }
}