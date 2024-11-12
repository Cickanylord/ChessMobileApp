package com.example.chessfrontend.ui.model

import ai_engine.board.BoardData
import com.example.chessfrontend.data.model.MatchEntity

data class MatchUiModel(
    val id: Long = -1L,
    val challenger: Long = -1L,
    val challenged: Long = -1L,
    val board: BoardData = BoardData("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1"),
    val isGoing: Boolean = true,
    val winner: Long = -1L,
    val loser: Long = -1L,
)



fun MatchEntity.toUiModel() = MatchUiModel(
    id = id,
    challenger = challenger,
    challenged = challenged,
    board = BoardData(board),
    isGoing = isGoing,
    winner = winner ?: -1L,
    loser = loser ?: -1L
)

fun MatchUiModel.toEntity() = MatchEntity(
    id = id,
    challenger = challenger,
    challenged = challenged,
    board = board.toString(),
    isGoing = isGoing,
    winner = winner,
    loser = loser
)

fun countWinLoseRatio(
    matches: List<MatchUiModel>,
    userId: Long,
): WinLoseRatioModel =
    WinLoseRatioModel(
        wins = matches.count { it.winner == userId },
        loses = matches.count { it.loser == userId }
    )