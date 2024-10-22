package com.example.chessfrontend.ui.viewmodels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chessfrontend.data.localStorage.UserPreferencesRepository
import com.example.chessfrontend.data.model.MessageEntity
import com.example.chessfrontend.data.model.MessageOutEntity
import com.example.chessfrontend.data.netwrok.ChessApiService
import com.example.chessfrontend.ui.model.MessageUiModel
import com.example.chessfrontend.ui.model.UserUiModel
import com.example.chessfrontend.ui.model.toUiModel
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
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
    private val userPreferencesRepository: UserPreferencesRepository,
    private val savedStateHandle: SavedStateHandle
): ViewModel() {
    var uiState: ChatUiState by mutableStateOf(ChatUiState())
        private set


    private lateinit var webSocket: WebSocket
    private val webSocketListener = ChatWebSocketListener()


    init {
        viewModelScope.launch {
            val friendJson = savedStateHandle.get<String>("userJson")
            uiState = uiState.copy(
                friend = Gson().fromJson(friendJson, UserUiModel::class.java)
            )
            val userId = userPreferencesRepository.getUserId().first()
            buildWebSocket(userId)
            loadMessages(uiState.friend?.id ?: -1L)
        }
    }

    fun buildWebSocket(userId: Long) {
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
                    receiverId = uiState.friend?.id ?: -1L,
                    text = message
                    )
                )
//                loadMessages(uiState.friend?.id ?: -1L)
//                Log.d("ChatViewModel", "Sent message: $message")

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
            val message = Gson().fromJson(text, MessageEntity::class.java).toUiModel()

            uiState = uiState.copy(
                messages = listOf(message) + uiState.messages
            )
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
    val friend: UserUiModel? = null,
    val user: UserUiModel? = null
)

sealed class ChatAction {
    data class LoadMessages(val partnerId: Long) : ChatAction()
    data class SendMessage(val message: String) : ChatAction()
}

