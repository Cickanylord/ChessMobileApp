package com.example.chessfrontend.ui.viewmodels.gameModes

import ai_engine.board.pieces.Piece
import ai_engine.board.pieces.PieceFactory
import ai_engine.board.pieces.Queen
import ai_engine.board.pieces.enums.PieceColor
import ai_engine.board.pieces.enums.PieceName
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
            is BoardAction.OnStepSoundOver -> onStepSoundOver()
            is BoardAction.OnPromotion -> onPromotion(action.pieceName)
        }
    }

    override fun onPromotion(pieceName: PieceName) {
        uiState.boardState.board.addPiece(
            PieceFactory.makePiece(
                pieceName,
                uiState.clickedPiece!!.pieceColor,
                uiState.clickedPiece!!.position
            )
        )
        uiState = uiState.copy(
            isWhitePromoting = false,
            isBlackPromoting = false,
            clickedPiece = null,
        )
    }

    private fun onStepSoundOver() {
        uiState = uiState.copy(
            playMoveSound = false
        )
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
            state
                .board
                .boardLogic
                .move(state.clickedPiece!!, move)

            val isWhitePromoting =  state.clickedPiece.name == PieceName.PAWN && move.first == 0
            val isBlackPromoting =  state.clickedPiece.name == PieceName.PAWN && move.first == 7

            uiState = if (isWhitePromoting || isBlackPromoting) {
                uiState.copy(
                    isWhitePromoting = isWhitePromoting,
                    isBlackPromoting = isBlackPromoting,
                    legalMoves = listOf()
                )
            } else {
                uiState.copy(
                    clickedPiece = null,
                    legalMoves = listOf(),
                )
            }
        }
        saveGame()
    }

    override fun saveGame() {}
}

