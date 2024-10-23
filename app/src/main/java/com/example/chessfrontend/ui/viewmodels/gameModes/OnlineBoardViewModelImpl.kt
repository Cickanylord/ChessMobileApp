package com.example.chessfrontend.ui.viewmodels.gameModes

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.chessfrontend.data.localStorage.UserPreferencesRepository
import com.example.chessfrontend.data.model.MatchEntity
import com.example.chessfrontend.data.model.StepRequestEntity
import com.example.chessfrontend.data.netwrok.ChessApiService
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
class OnlineBoardViewModelImpl @Inject constructor(
    private val chessApiService: ChessApiService,
    private val userPreferencesRepository: UserPreferencesRepository,
    savedStateHandle: SavedStateHandle
) : BoardViewModelImpl() {

    private lateinit var webSocket: WebSocket
    private val webSocketListener = MatchWebSocketListener()
    private val id:Long

    private fun buildWebSocket(matchId: Long) {
        val request = Request.Builder()
            .url("ws://192.168.0.89:8080/match?$matchId")
            .build()

        val client = OkHttpClient()
        webSocket = client.newWebSocket(request, webSocketListener)
    }

    init {
        val matchId = savedStateHandle.get<Long>("matchId")!!

        id = matchId

        loadBoard()

        viewModelScope.launch {
            val userId = userPreferencesRepository.getUserId().first()
            buildWebSocket(userId)
        }
    }

    override fun loadBoard() {
        viewModelScope.launch {
            try {
                val match = chessApiService.getMatch(id).toUiModel()

                uiState = uiState.copy(
                    boardState = mutableStateOf(match.board)
                )

            } catch (e: Exception) {
                Log.d("OnlineBoardViewModelImpl", "loadBoard: $e")
            }
        }
    }

    override fun step(move: Pair<Int, Int>) {
        super.step(move)
        viewModelScope.launch {
            try {
                chessApiService.step(
                    StepRequestEntity(
                        matchId = id,
                        board = uiState.board.toString()
                    )
                )
            } catch (e: Exception) {
                Log.d("OnlineBoardViewModelImpl", "step: $e")
                /** rollback board to consistent state from server*/
                loadBoard()
            }
        }
    }

    inner class MatchWebSocketListener : WebSocketListener() {
        override fun onOpen(webSocket: WebSocket, response: Response) {
            Log.d("OnlineBoardViewModelImpl", "WebSocket opened")
        }

        override fun onMessage(webSocket: WebSocket, text: String) {
            Log.d("OnlineBoardViewModelImpl", "Received message: $text")
            val matchUiModel = Gson().fromJson(text, MatchEntity::class.java).toUiModel()
            if (matchUiModel.id == id) {
                uiState = uiState.copy(
                    boardState = mutableStateOf(matchUiModel.board)
                )
            }
        }

        override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
            Log.d("OnlineBoardViewModelImpl", "WebSocket closing: $code / $reason")
            webSocket.close(1000, null)
        }

        override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
            Log.e("OnlineBoardViewModelImpl", "WebSocket failure: ${t.message}")
        }
    }
}