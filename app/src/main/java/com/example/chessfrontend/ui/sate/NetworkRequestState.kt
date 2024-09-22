package com.example.chessfrontend.ui.sate

import kotlinx.coroutines.flow.MutableStateFlow

sealed interface NetworkRequestState {
    data object Success : NetworkRequestState
    data object Error : NetworkRequestState
    data object Loading : NetworkRequestState
    data object Normal: NetworkRequestState
}