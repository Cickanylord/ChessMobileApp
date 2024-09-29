package com.example.chessfrontend.ui.viewmodels.gameModes

import ai_engine.ai.NewAI
import ai_engine.board.pieces.Piece
import ai_engine.board.pieces.enums.PieceColor
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.auth.bme.chess.ai_engine.board.BoardData
import com.example.chessfrontend.data.localStorage.UserPreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
open class BoardViewModel @Inject constructor(
    private val userPreferencesRepository: UserPreferencesRepository
): ViewModel()  {
    var uiState by mutableStateOf(BoardUiState())

    init {
        loadBoard()
    }

    private fun loadBoard() {
        viewModelScope.launch {
            userPreferencesRepository
                .getOfflineGame()
                .collect { fen ->
                    uiState = if (fen == "") {
                        BoardUiState()
                    } else {
                        uiState.copy(
                            board = BoardData(fen)
                        )
                    }
                }
        }
    }

    open fun handleAction(action: BoardAction) {
        when (action) {
            is BoardAction.Step -> step(action.move)
            is BoardAction.PieceClicked -> pieceClicked(action.piece)
        }
    }

    /**
     *  This function gets the available steps when a piece is clicked.
     *
     *  @param piece the piece which is clicked
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

    /**
     *  This function moves the chosen piece to the chosen tile.
     *
     *  @param move the tile which is chosen to step on
     */

    private fun step(move: Pair<Int, Int>) {
        uiState.let { state ->
            state.board.boardLogic.move(state.clickedPiece!!, move)
            uiState = uiState.copy(
                clickedPiece = null,
                legalMoves = listOf()
            )
        }
        viewModelScope.launch {
            userPreferencesRepository.saveOfflineGame(uiState.board.fen.toString())
            val ai = NewAI(PieceColor.BLACK, uiState.board)
            val nextStep = ai.getTheNextStep()
            uiState.board.boardLogic.move(nextStep.first, nextStep.second)

            uiState = uiState.copy(
                clickedPiece = null,
                legalMoves = listOf()
            )
        }
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
