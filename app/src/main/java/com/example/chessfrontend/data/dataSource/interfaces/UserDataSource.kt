package com.example.chessfrontend.data.dataSource.interfaces

import com.example.chessfrontend.data.model.UserEntity

interface UserDataSource {
    suspend fun getProfile(): UserEntity
    suspend fun getFriends(): List<UserEntity>
    suspend fun getAllUsers(): List<UserEntity>
    suspend fun addFriend(id: Long)
    suspend fun logOut()
}