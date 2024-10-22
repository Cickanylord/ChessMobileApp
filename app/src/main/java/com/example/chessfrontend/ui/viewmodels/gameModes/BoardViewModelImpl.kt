package com.example.chessfrontend.ui.viewmodels.gameModes

import ai_engine.board.pieces.Piece
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
open class BoardViewModelImpl @Inject constructor() : ViewModel(), BoardViewModel {
    override var uiState by mutableStateOf(BoardUiState())
        internal set

    override fun loadBoard() { }

    override fun handleAction(action: BoardAction) {
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
    override fun pieceClicked(piece: Piece) {
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

    override fun step(move: Pair<Int, Int>) {
        uiState.let { state ->
            state.board
                .boardLogic
                .move(state.clickedPiece!!, move)

            uiState = uiState.copy(
                clickedPiece = null,
                legalMoves = listOf()
            )
        }
        saveGame()
    }

    override fun saveGame() {}
}

