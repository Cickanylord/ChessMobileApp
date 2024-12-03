package com.example.chessfrontend.ui.viewmodels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chessfrontend.data.UserRepository
import com.example.chessfrontend.data.model.MessageEntity
import com.example.chessfrontend.data.model.MessageOutEntity
import com.example.chessfrontend.data.netwrok.ChessApiService
import com.example.chessfrontend.ui.model.MessageUiModel
import com.example.chessfrontend.ui.model.UserUiModel
import com.example.chessfrontend.ui.model.toUiModel
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val chessApiService: ChessApiService,
    private val savedStateHandle: SavedStateHandle,
    private val userRepository: UserRepository,
): ViewModel() {
    var uiState: ChatUiState by mutableStateOf(ChatUiState())
        private set


    private lateinit var webSocket: WebSocket
    private val webSocketListener = ChatWebSocketListener()


    init {
        viewModelScope.launch {
            val friendId = savedStateHandle.get<Long>("userId")
            println(friendId)

            uiState = uiState.copy(
                friend = userRepository.combinedUsers.value?.find { it.id == friendId }?.toUiModel()!!,
                user = userRepository.profile.value?.toUiModel()!!
            )

            buildWebSocket(uiState.user.id)
            loadMessages(uiState.friend.id)
        }
    }

    private fun buildWebSocket(userId: Long) {
        val request = Request.Builder()
            .url("ws://192.168.0.89:8080/chat?${userId}")
            .build()

        val client = OkHttpClient()
        webSocket = client.newWebSocket(request, webSocketListener)
    }

    fun handleAction(action: ChatAction) {
        when (action) {
            is ChatAction.LoadMessages -> loadMessages(action.partnerId)
            is ChatAction.SendMessage -> sendMessage(action.message)
        }
    }

    private fun sendMessage(message: String) {
        viewModelScope.launch {
            try {
                val response = chessApiService.sendMessage(
                    MessageOutEntity(
                    receiverId = uiState.friend.id,
                    text = message
                    )
                )
            } catch (e: Exception) {
                Log.e("ChatViewModel", "Error sending message: ${e.message}")
            }
        }
    }
    override fun onCleared() {
        super.onCleared()
        webSocket.close(1000, "ViewModel cleared")
    }

    private fun loadMessages(chatId: Long) {
        viewModelScope.launch {
            try {
                val messages = chessApiService.getMessageBetweenUsers(chatId)
                uiState = uiState
                    .copy(
                        messages = messages
                            .map { it.toUiModel() }
                            .sortedBy { it.sentDate }
                            .asReversed()
                    )
                Log.d("ChatViewModel", "Loaded ${uiState.messages} messages")

            } catch (e: Exception) {
                Log.e("ChatViewModel", "Error loading messages: ${e.message}")
            }
        }
    }
    inner class ChatWebSocketListener : WebSocketListener() {
        override fun onOpen(webSocket: WebSocket, response: Response) {
            Log.d("ChatViewModel", "WebSocket opened")
        }

        override fun onMessage(webSocket: WebSocket, text: String) {
            Log.d("ChatViewModel", "Received message: $text")
            //loadMessages(uiState.friend.id)
            val message = Gson().fromJson(text, MessageEntity::class.java).toUiModel()
                if (message.sender == uiState.friend.id || message.sender == uiState.user.id) {
                    uiState = uiState.copy(
                        messages = listOf(message) + uiState.messages
                    )

            }
        }

        override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
            Log.d("ChatViewModel", "WebSocket closing: $code / $reason")
            webSocket.close(1000, null)
        }

        override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
            Log.e("ChatViewModel", "WebSocket failure: ${t.message}")
        }
    }
}


data class ChatUiState(
    val messages: List<MessageUiModel> = listOf(),
    val friend: UserUiModel = UserUiModel(),
    val user: UserUiModel = UserUiModel(),
)

sealed interface ChatAction {
    data class LoadMessages(val partnerId: Long) : ChatAction
    data class SendMessage(val message: String) : ChatAction
}

