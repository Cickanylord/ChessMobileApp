package com.example.chessfrontend.ui.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chessfrontend.netwrok.ChessApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val chessApiService: ChessApiService,
): ViewModel() {
    var uiState by mutableStateOf(ProfileUiState())
        private set

    init {
        viewModelScope.launch {
            chessApiService.getProfile()
            uiState = uiState.copy(
                userName = chessApiService.getProfile().name,
                email
               = chessApiService.getProfile().name

            )
        }
    }
}

data class ProfileUiState(
    val userName: String = "",
    val email: String = "",
)
