package com.example.chessfrontend.ui.viewmodels.gameModes

import ai_engine.board.BoardData
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.chessfrontend.data.localStorage.LocalStorage
import com.example.chessfrontend.data.netwrok.ChessApiService
import com.example.chessfrontend.ui.model.MatchUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import javax.inject.Inject

@HiltViewModel
class AiBoardViewModelImpl @Inject constructor(
    private val chessApiService: ChessApiService,
    private val localStorage: LocalStorage
): BoardViewModelImpl() {

    override fun step(move: Pair<Int, Int>) {
        super.step(move)
        viewModelScope.launch {
            try {
                Log.d("AiBoardViewModelImpl", localStorage.getToken().first().accessToken)
                val response = chessApiService.getAi(FenDTO(uiState.boardState.board.toString()))
                Log.d("AiBoardViewModelImpl", response.toString())
                uiState = uiState.copy(
                    boardState = MatchUiModel(
                        board = BoardData(response.fen)
                    )
                )

            } catch (e: Exception) {
                Log.d("AiBoardViewModelImpl", e.message.toString())
            }
        }
    }

    override fun loadBoard() {
        viewModelScope.launch {
            val game = localStorage.getAiGame().first()
            uiState = uiState.copy(
                boardState = MatchUiModel(
                    board = BoardData(game)
                )
            )
        }
    }

    override fun saveGame() {
        viewModelScope.launch {
            localStorage.saveAiGame(uiState.boardState.board.toString())
        }
    }

}
@Serializable
data class FenDTO(val fen: String)