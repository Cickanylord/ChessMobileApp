package com.example.chessfrontend.data

import androidx.lifecycle.LiveData
import com.example.chessfrontend.data.model.UserEntity

interface UserRepository {
    val users: LiveData<List<UserEntity>>
    val friends: LiveData<List<UserEntity>>
    val profile: LiveData<UserEntity>
    val combinedUsers: LiveData<List<UserEntity>>

    suspend fun getProfile()
    suspend fun getFriends()
    suspend fun getAllUsers()

}