package com.example.chessfrontend.ui.viewmodels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chessfrontend.data.localStorage.UserPreferencesRepository
import com.example.chessfrontend.data.netwrok.ChessApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val chessApiService: ChessApiService,
    private val userPreferencesRepository: UserPreferencesRepository
): ViewModel() {
    var uiState by mutableStateOf(ProfileUiState())
        private set

    init {
        loadData()
    }

    fun handleAction(action: ProfileAction) {
        when (action) {
            is ProfileAction.LoadData -> loadData()
            is ProfileAction.Logout -> logout()
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
                userPreferencesRepository.storeUserId(profile)

            } catch (e: Exception) {
                Log.e("ProfileViewModel", "Error loading data", e)
            }
        }
    }

    private fun logout(){
        viewModelScope.launch {
            try {
                userPreferencesRepository.logout()
                uiState = uiState.copy(
                    logout = true
                )
            } catch (e: Exception) {
                Log.e("ProfileViewModel", "Error loading data", e)
            }
        }
    }
}

sealed interface ProfileAction {
    data object LoadData : ProfileAction
    data object Logout : ProfileAction
}

data class ProfileUiState(
    val userName: String = "",
    val email: String = "",
    val logout: Boolean = false
)
