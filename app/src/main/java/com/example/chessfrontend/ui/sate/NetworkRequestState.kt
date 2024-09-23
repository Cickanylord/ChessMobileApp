package com.example.chessfrontend.ui.sate

sealed interface NetworkRequestState {
    data object Success : NetworkRequestState
    data object Error : NetworkRequestState
    data object Loading : NetworkRequestState
    data object Normal: NetworkRequestState
}