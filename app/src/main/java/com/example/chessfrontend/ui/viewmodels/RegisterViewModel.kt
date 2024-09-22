package com.example.chessfrontend.ui.viewmodels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chessfrontend.data.model.UserPost
import com.example.chessfrontend.netwrok.ChessApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val chessApiService: ChessApiService
): ViewModel() {
    var uiState by mutableStateOf(RegisterUiState())
        private set

    fun handleAction(action: RegisterAction) {
        when(action){
            is RegisterAction.Register -> register()
            is RegisterAction.ToggleToast -> toggleToast()
            is RegisterAction.SetUserName -> setUserName(action.userName)
            is RegisterAction.SetEmail -> setEmail(action.email)
            is RegisterAction.SetPassword -> setPassword(action.password)
            is RegisterAction.SetPassword2 -> setPassword2(action.password2)
        }
    }

    fun register(){

        viewModelScope.launch {
            try {
                validateInput()?.let { errorMessage ->
                    setToastMessage(errorMessage)
                    toggleToast()
                    return@launch
                }
                chessApiService.register(UserPost(uiState.userName, uiState.password))
                registered()
            } catch (e: Exception) {
                Log.e("Register", e.toString())
            }
        }
    }

    private fun validateInput(): String? {
        return when {
            uiState.userName.length < 3 -> "Username must be at least 3 characters long."
            uiState.email.length < 3 -> "Please enter a valid email address."
            uiState.password != uiState.password2 -> "Passwords do not match."
            else -> null
        }
    }

    private fun setPassword2(password2: String) {
        uiState = uiState.copy (
            password2 = password2
        )
    }

    private fun setPassword(password: String) {
        uiState = uiState.copy (
            password = password
        )
    }

    private fun setEmail(email: String) {
        uiState = uiState.copy (
            email = email
        )
    }

    private fun setUserName(userName: String) {
        uiState = uiState.copy (
            userName = userName
        )
    }

    private fun toggleToast() {
        uiState.showToast.let {
            uiState = uiState.copy (
                showToast = !it
            )
        }
    }

    private fun setToastMessage(message: String) {
        uiState = uiState.copy (
            toastMessage = message
        )
    }

    private fun registered() {
        uiState = uiState.copy (
            isRegistered = true
        )
    }
}


data class RegisterUiState(
    var userName: String = "",
    var email: String = "",
    var password: String = "",
    var password2: String = "",
    val showToast: Boolean = false,
    val toastMessage: String = "",
    val isRegistered: Boolean = false
)

sealed interface RegisterAction {
    data object Register : RegisterAction
    data object ToggleToast : RegisterAction
    data class SetUserName(val userName: String) : RegisterAction
    data class SetEmail(val email: String) : RegisterAction
    data class SetPassword(val password: String) : RegisterAction
    data class SetPassword2(val password2: String) : RegisterAction
}
