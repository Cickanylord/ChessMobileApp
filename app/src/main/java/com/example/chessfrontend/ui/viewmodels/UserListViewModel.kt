package com.example.chessfrontend.ui.viewmodels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.chessfrontend.data.model.FriendRequestEntity
import com.example.chessfrontend.data.model.UserEntity
import com.example.chessfrontend.data.netwrok.ChessApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserListViewModel @Inject constructor(
    private val chessApiService: ChessApiService
) : ViewModel() {
    var uiState by mutableStateOf(UserListState())
        private set

    init {
        loadUsers()
    }

    fun handleAction(action: UserListAction) {
        when (action) {
            is UserListAction.AddFriend -> addFriend(action.id)
        }
    }

    private fun addFriend(id: Long) {
        viewModelScope.launch {
            try {
                chessApiService.addFriend(FriendRequestEntity(id))
            } catch (e: Exception) {
                Log.e("UserListViewModel", "Error adding friend: ${e.message}")
            }
        }
    }

    private fun loadUsers() {
        viewModelScope.launch {
            try {
                val users = chessApiService.getUsers()
                uiState = uiState.copy(
                    users = users.map { it.toUiModel() }
                )
            } catch (e: Exception) {
                Log.e("UserListViewModel", "Error loading users: ${e.message}")
            }
        }
    }
}

sealed interface UserListAction {
    data class AddFriend(val id: Long) : UserListAction
}

data class UserListState(
    val users: List<UserListUiModel> = listOf()
)

data class UserListUiModel (
    val id: Long,
    val userName: String,
)

fun UserEntity.toUiModel() = UserListUiModel(
    id = id,
    userName = name,
)


