package com.example.chessfrontend.ui.viewmodels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chessfrontend.data.localStorage.UserPreferencesRepository
import com.example.chessfrontend.data.model.MessageEntity
import com.example.chessfrontend.data.netwrok.ChessApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val chessApiService: ChessApiService,
    private val userPreferencesRepository: UserPreferencesRepository
): ViewModel() {
    var uiState: ChatUiState by mutableStateOf(ChatUiState())
        private set

    init {
        loadMessages()
    }

    fun handleEvent(event: ChatEvent) {
        when (event) {
            is ChatEvent.LoadMessages -> loadMessages()
            is ChatEvent.SendMessage -> sendMessage(event.message)
        }
    }

    private fun sendMessage(message: String) {
        TODO("Not yet implemented")
    }

    private fun loadMessages() {
        viewModelScope.launch {
            try {
                val messages = chessApiService.getMessageBetweenUsers(4L)
                uiState = uiState
                    .copy(
                        messages = messages
                            .toMutableList()
                            .apply { shuffle() }
                    )
                Log.d("ChatViewModel", "Loaded ${uiState.messages} messages")

            } catch (e: Exception) {
                Log.e("ChatViewModel", "Error loading messages: ${e.message}")
            }
        }
    }
}


    data class ChatUiState(
        val messages: List<MessageEntity> = listOf()
    )

    sealed class ChatEvent {
        data class LoadMessages(val partnerId: Long) : ChatEvent()
        data class SendMessage(val message: String) : ChatEvent()
    }

