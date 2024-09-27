package com.example.chessfrontend.ui.viewmodels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chessfrontend.data.netwrok.ChessApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val chessApiService: ChessApiService,
): ViewModel() {
    var uiState by mutableStateOf(ProfileUiState())
        private set

    init {
        loadData()
    }

    fun handleAction(action: ProfileAction) {
        when (action) {
            is ProfileAction.LoadData -> loadData()
        }
    }

    private fun loadData() {
        viewModelScope.launch {
            try {
                val profile = chessApiService.getProfile()
                uiState = uiState.copy(
                    userName = profile.name,
                    email = profile.name
                )
            } catch (e: Exception) {
                Log.e("FriendListViewModel", "Error loading data", e)
            }
        }
    }
}

sealed interface ProfileAction {
    data object LoadData : ProfileAction
}

data class ProfileUiState(
    val userName: String = "",
    val email: String = "",
)
