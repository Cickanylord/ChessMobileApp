package com.example.chessfrontend.ui.viewmodels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chessfrontend.data.netwrok.ChessApiService
import com.example.chessfrontend.ui.model.UserUiModel
import com.example.chessfrontend.ui.model.toUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@Deprecated("use drawer instead")
@HiltViewModel
class FriendListViewModel@Inject constructor(
    private val chessApiService: ChessApiService,
) : ViewModel() {
    var uiState by mutableStateOf(FriendListUiState())
        private set


    init {
        loadData()
    }

    fun handleAction(action: FriendListAction) {
        when (action) {
            is FriendListAction.LoadData -> loadData()
        }
    }
    private fun loadData() {
        viewModelScope.launch {
            try {
                val friends = chessApiService.getFriends()
                uiState = uiState.copy(
                    friends = friends.map { it.toUiModel() }
                )
            } catch (e: Exception) {
                Log.e("FriendListViewModel", "Error loading data", e)
            }
        }
    }
}

sealed interface FriendListAction {
    data object LoadData : FriendListAction
}

data class FriendListUiState (
    val friends: List<UserUiModel> = emptyList()
)
