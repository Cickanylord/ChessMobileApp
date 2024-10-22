package com.example.chessfrontend.ui.model

import ai_engine.board.BoardData
import com.example.chessfrontend.data.model.MatchEntity


data class MatchUiModel (
    val id: Long,
    val challenger: Long,
    val challenged: Long,
    val board: BoardData
)

fun MatchEntity.toUiModel() = MatchUiModel(
    id = id,
    challenger = challenger,
    challenged = challenged,
    board = BoardData(board)
)

fun MatchUiModel.toEntity() = MatchEntity(
    id = id,
    challenger = challenger,
    challenged = challenged,
    board = board.toString()
)