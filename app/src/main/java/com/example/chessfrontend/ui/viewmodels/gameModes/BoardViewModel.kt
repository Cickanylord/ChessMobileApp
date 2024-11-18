package com.example.chessfrontend.ui.viewmodels.gameModes

import ai_engine.board.BoardData
import ai_engine.board.pieces.Piece
import com.example.chessfrontend.ui.model.MatchUiModel
import com.example.chessfrontend.ui.model.UserUiModel


const val DEFAULT_GAME: String  = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1"

interface BoardViewModel {
    val uiState: BoardUiState

    fun handleAction(action: BoardAction)

    fun loadBoard()

    fun pieceClicked(piece: Piece)

    fun step(move: Pair<Int, Int>)

    fun saveGame()
}

sealed interface BoardAction {
    data class PieceClicked(val piece: Piece) : BoardAction
    data class Step(val move: Pair<Int, Int>) : BoardAction
    data object OnStepSoundOver : BoardAction
}

data class BoardUiState(
    var boardState: MatchUiModel = MatchUiModel(),
    val legalMoves: List<Pair<Int, Int>> = listOf(),
    val clickedPiece: Piece? = null,
    val user: UserUiModel? = null,
    val opponent: UserUiModel? = null,
    val matchId: Long = -1L,
    val playMoveSound: Boolean = false
) {
    val board: BoardData
        get() = boardState.board
}