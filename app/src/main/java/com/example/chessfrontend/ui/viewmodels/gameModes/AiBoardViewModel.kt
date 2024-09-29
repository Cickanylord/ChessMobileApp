package com.example.chessfrontend.ui.viewmodels.gameModes

import ai_engine.board.pieces.Piece
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.auth.bme.chess.ai_engine.board.BoardData
import com.example.chessfrontend.data.localStorage.UserPreferencesRepository
import com.example.chessfrontend.data.netwrok.ChessApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import javax.inject.Inject

@HiltViewModel
class AiBoardViewModel @Inject constructor(
    private val chessApiService: ChessApiService,
    private val userPreferencesRepository: UserPreferencesRepository
): BoardViewModel(userPreferencesRepository) {

    override fun handleAction(action: BoardAction) {
        when (action) {
            is BoardAction.Step -> step(action.move)
            is BoardAction.PieceClicked -> pieceClicked(action.piece)
        }
    }

    fun step(move: Pair<Int, Int>) {
        super.handleAction(BoardAction.Step(move))
        viewModelScope.launch {
            try {
                Log.d("AiBoardViewModel", userPreferencesRepository.getToken().first().accessToken)
                val response = chessApiService.getAi(FenDTO(uiState.board.fen.toString()))
                Log.d("AiBoardViewModel", response.toString())
                uiState = uiState.copy(
                    board = BoardData(response.fen)
                )

            } catch (e: Exception) {
                Log.d("AiBoardViewModel", e.message.toString())
            }
        }
    }

    fun pieceClicked(piece: Piece) {
        super.handleAction(BoardAction.PieceClicked(piece))
    }
}
@Serializable
data class FenDTO(val fen: String)