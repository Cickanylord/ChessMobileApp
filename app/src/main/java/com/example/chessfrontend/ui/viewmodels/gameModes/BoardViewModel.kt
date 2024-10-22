package com.example.chessfrontend.ui.viewmodels.gameModes

import ai_engine.board.BoardData
import ai_engine.board.pieces.Piece
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf


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
}

data class BoardUiState(
    var boardState: MutableState<BoardData> = mutableStateOf(BoardData("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1")),
    val legalMoves: List<Pair<Int, Int>> = listOf(),
    val clickedPiece: Piece? = null
) {
    val board: BoardData
        get() = boardState.value
}
