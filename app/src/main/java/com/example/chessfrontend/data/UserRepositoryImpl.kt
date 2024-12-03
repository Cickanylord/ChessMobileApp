package com.example.chessfrontend.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.example.chessfrontend.data.dataSource.interfaces.UserDataSource
import com.example.chessfrontend.data.model.UserEntity

class UserRepositoryImpl (
    private val dataSource: UserDataSource
): UserRepository {
    private val _users = MutableLiveData<List<UserEntity>>()
    override val users: LiveData<List<UserEntity>> = _users

    private val _friends = MutableLiveData<List<UserEntity>>()
    override val friends: LiveData<List<UserEntity>> = _friends

    private val _profile = MutableLiveData<UserEntity>()
    override val profile: LiveData<UserEntity> = _profile

    private val _combinedUsers = MediatorLiveData<List<UserEntity>>().apply {
        addSource(_users) { users ->
            value = insertUserToList(value ?: emptyList(), users)
        }
        addSource(_profile) { profile ->
            value = insertUserToList(value ?: emptyList(), listOf(profile))
        }
        addSource(_friends) { friends ->
            value = insertUserToList(value ?: emptyList(), friends)
        }
    }

    override val combinedUsers: LiveData<List<UserEntity>> = _combinedUsers

    override suspend fun getProfile() {
        _profile.value = dataSource.getProfile()
    }

    override suspend fun getFriends() {
        _friends.value = dataSource.getFriends()
    }

    override suspend fun getAllUsers() {
        _users.value = dataSource.getAllUsers()
    }

    override suspend fun addFriend(id: Long) {
        dataSource.addFriend(id)
        getProfile()
        _friends.value = insertUserToList(friends.value ?: listOf(), listOfNotNull( users.value?.find { it.id == id }))
    }

    override suspend fun logOut() {
        dataSource.logOut()
    }

    private fun findUserNameById(id: Long): String {
        return combinedUsers.value?.find { it.id == id }?.name ?: "Not Found"
    }

    private fun insertUserToList(oldUsers: List<UserEntity>, newUsers: List<UserEntity>): List<UserEntity> {
        val newUserIds = newUsers.map { it.id }.toSet()
        return  oldUsers.filterNot { it.id in newUserIds } + newUsers
    }

}