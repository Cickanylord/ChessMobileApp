package com.example.chessfrontend.ui.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chessfrontend.data.model.User
import com.example.chessfrontend.netwrok.ChessApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FriendListViewModel@Inject constructor(
    private val chessApiService: ChessApiService,
) : ViewModel() {
    var uiState by mutableStateOf(FriendListUiState())
        private set

    init {
        viewModelScope.launch {
            val friends = chessApiService.getFriends()
            uiState = uiState.copy(
                friends = friends
            )
        }
    }
}

data class FriendListUiState (
    val friends: List<User> = emptyList()
)
