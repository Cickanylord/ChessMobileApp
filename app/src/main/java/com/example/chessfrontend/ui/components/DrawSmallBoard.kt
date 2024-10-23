package com.example.chessfrontend.ui.components

import ai_engine.board.BoardData
import android.annotation.SuppressLint
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.chessfrontend.ui.model.MatchUiModel
import com.example.chessfrontend.ui.viewmodels.gameModes.BoardUiState

@SuppressLint("UnrememberedMutableState")
@Composable
fun DrawSmallBoard(
    tileSize: Int,
    match: MatchUiModel = MatchUiModel(
        id = -1L,
        challenger = -1L,
        challenged = -1L,
        board = BoardData("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1")
    )
) {
    Box(
        modifier = Modifier
            .size(8.dp * tileSize)
            .border(
                width = 2.dp,
                color = Color.Gray,
                shape = RoundedCornerShape(16.dp)
            )
            .clip(RoundedCornerShape(16.dp))
    ) {
        BoardScreenContent(
            state = BoardUiState(
                boardState = mutableStateOf(match.board),
            ),
            onAction = {},
            tileSize = tileSize,
            clickable = false
        )
    }
}