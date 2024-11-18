package com.example.chessfrontend.ui.viewmodels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chessfrontend.data.model.UserAuth
import com.example.chessfrontend.data.netwrok.ChessApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val chessApiService: ChessApiService,
) : ViewModel() {
    var uiState by mutableStateOf(LoginUiState())
        private set


    fun handleAction(action: LoginAction) {
        when (action) {
            is LoginAction.Login -> login()
            is LoginAction.ForgotPassword -> {}
            is LoginAction.ToggleToast -> toggleToast()
            is LoginAction.SetUserName -> setUserName(action.userName)
            is LoginAction.SetPassword -> setPassword(action.password)
        }
    }

    private fun login() {
        viewModelScope.launch {
            try {
                val token = chessApiService.authenticate(UserAuth(uiState.userName, uiState.password))
                logIn()
                setToken(token.accessToken)
            } catch (e: Exception) {
                Log.e("Login", "$e")
                setToastMessage("Login failed")
                toggleToast()
            }
        }
    }

    private fun logIn() {
        uiState = uiState.copy(
            isLoggedIn = true
        )
    }

    private fun setToastMessage(message: String) {
        uiState = uiState.copy(
            toastMessage = message
        )
    }

    private fun toggleToast() {
        uiState.showToast.let {
            uiState = uiState.copy(
                showToast = !it
            )
        }
    }

    private fun setUserName(userName: String) {
        uiState = uiState.copy(
            userName = userName
        )
    }

    private fun setPassword(password: String) {
        uiState = uiState.copy(
            password = password
        )
    }

    private fun setToken(token: String) {
        uiState = uiState.copy(
            token = token
        )
    }
}

data class LoginUiState (
    val userName: String = "",
    val password: String = "",
    val toastMessage: String = "",
    val isLoggedIn: Boolean = false,
    val showToast: Boolean = false,
    val token: String = "",
)

sealed interface LoginAction {
    data object Login : LoginAction
    data object ForgotPassword : LoginAction
    data object ToggleToast : LoginAction
    data class SetUserName(val userName: String) : LoginAction
    data class SetPassword(val password: String) : LoginAction
}
