package com.example.chessfrontend.ui.viewmodels

import android.graphics.Bitmap
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.chessfrontend.data.UserRepository
import com.example.chessfrontend.data.model.FriendRequestEntity
import com.example.chessfrontend.data.model.UserEntity
import com.example.chessfrontend.data.netwrok.ChessApiService
import com.example.chessfrontend.ui.model.UserUiModel
import com.example.chessfrontend.ui.model.toUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserListViewModel @Inject constructor(
    private val chessApiService: ChessApiService,
    private val userRepository: UserRepository
) : ViewModel() {
    var uiState by mutableStateOf(UserListState())
        private set

    init {
        observeUsers()
        loadUsers()
    }

    fun handleAction(action: UserListAction) {
        when (action) {
            is UserListAction.AddFriend -> addFriend(action.id)
        }
    }

    private fun addFriend(id: Long) {
        viewModelScope.launch {
            userRepository.addFriend(id)
        }
    }

    private fun loadUsers() {
        viewModelScope.launch {
            userRepository.getAllUsers()
        }
    }

    private fun observeUsers() {
        viewModelScope.launch {
            userRepository.combinedUsers.observeForever() {
                uiState = uiState.copy(
                    users = userRepository
                        .users
                        .value
                        ?.filter { userRepository.profile.value?.id != it.id && userRepository.profile.value?.friendList?.contains(it.id) == false }
                        ?.map { it.toUiModel() }
                        ?: listOf(),
                )
            }
        }
    }
}



sealed interface UserListAction {
    data class AddFriend(val id: Long) : UserListAction
}

data class UserListState(
    val users: List<UserUiModel> = listOf()
)


