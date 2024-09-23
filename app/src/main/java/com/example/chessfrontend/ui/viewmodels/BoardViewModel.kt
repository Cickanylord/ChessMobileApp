package com.example.chessfrontend.ui.viewmodels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.hu.bme.aut.chess.ai_engine.board.BoardData
import com.hu.bme.aut.chess.ai_engine.board.pieces.peice_interface.Piece
import dagger.hilt.android.lifecycle.HiltViewModel

class BoardViewModel: ViewModel()  {
    var uiState by mutableStateOf(BoardUiState())

    fun handleAction(action: BoardAction) {
        when (action) {
            is BoardAction.Step -> step(action.move)
            is BoardAction.PieceClicked -> pieceClicked(action.piece)
        }
    }

    /**
     *  This function gets the available steps when a piece is clicked.
     *
     *  @param piece the piece which is clicked
     *
     */
    private fun pieceClicked(piece: Piece) {
        uiState.let { state ->
            uiState = uiState.copy(
                legalMoves = state.board
                    .boardLogic
                    .getLegalMoves(piece),
                clickedPiece = piece
            )
        }

    }

    private fun step(move: Pair<Int, Int>) {
        uiState.let { state ->
            state.board.boardLogic.move(state.clickedPiece!!, move)
            uiState = uiState.copy(
                clickedPiece = null,
                legalMoves = listOf()
            )
        }
        Log.d("BoardViewModel", "legal moves: ${uiState.board.toString()}")
    }

}
sealed interface BoardAction {
    data class PieceClicked(val piece: Piece) : BoardAction
    data class Step(val move: Pair<Int, Int>) : BoardAction
}
data class BoardUiState(
    val board: BoardData = BoardData("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1"),
    val legalMoves: List<Pair<Int, Int>> = listOf(),
    val clickedPiece: Piece? = null
)
