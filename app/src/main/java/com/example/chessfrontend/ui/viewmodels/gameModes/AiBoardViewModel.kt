package com.example.chessfrontend.ui.viewmodels.gameModes

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.chessfrontend.netwrok.ChessApiService
import com.hu.bme.aut.chess.ai_engine.board.BoardData
import com.hu.bme.aut.chess.ai_engine.board.pieces.peice_interface.Piece
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import javax.inject.Inject

@HiltViewModel
class AiBoardViewModel @Inject constructor(
    private val chessApiService: ChessApiService
): BoardViewModel() {

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