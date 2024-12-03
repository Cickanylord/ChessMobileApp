package com.example.chessfrontend.data.dataSource

import android.util.Log
import com.example.chessfrontend.data.dataSource.interfaces.UserDataSource
import com.example.chessfrontend.data.localStorage.LocalStorage
import com.example.chessfrontend.data.model.FriendRequestEntity
import com.example.chessfrontend.data.model.UserEntity
import com.example.chessfrontend.data.netwrok.ChessApiService
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class UserDataSourceImpl @Inject constructor(
    val localStorage: LocalStorage,
    val chessApiService: ChessApiService
): UserDataSource {

    override suspend fun getProfile(): UserEntity {
        val savedProfile = localStorage.getUser().first()
        try {
            val fetchedUser = chessApiService.getProfile()
            if (fetchedUser != savedProfile) {
                localStorage.storeUser(fetchedUser)
            }
            return fetchedUser
        } catch (e: Exception) {
            Log.e("UserDataSource", "Error fetching user data", e)
            return savedProfile!!
        }
    }

    override suspend fun getFriends(): List<UserEntity> {
        try {
            return chessApiService.getFriends()
        } catch (e: Exception) {
            Log.e("UserDataSource", "Error fetching friends data", e)
            return emptyList()
        }
    }

    override suspend fun getAllUsers(): List<UserEntity> {
        try {
            return chessApiService.getUsers()
        } catch (e: Exception) {
            Log.e("UserDataSource", "Error fetching matches data", e)
            return emptyList()
        }
    }

    override suspend fun addFriend(id: Long) {
        try {
            chessApiService.addFriend(FriendRequestEntity(id))
        } catch (e: Exception) {
            Log.e("UserDataSource", "Error adding friend", e)
        }
    }

    override suspend fun logOut() {
        localStorage.logout()
    }
}