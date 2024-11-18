package com.example.chessfrontend.ui.components

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
import com.example.chessfrontend.ui.model.UserUiModel
import com.example.chessfrontend.ui.viewmodels.gameModes.BoardUiState

@Composable
fun DrawSmallBoard(
    modifier: Modifier = Modifier,
    tileSize: Int,
    match: MatchUiModel = MatchUiModel(),
) {
    Box(
        modifier = modifier
            .size(8.dp * tileSize)
            .border(
                width = 2.dp,
                color = Color.Gray.copy(alpha = 0.5f),
                shape = RoundedCornerShape(16.dp)
            )
            .clip(RoundedCornerShape(16.dp))
    ) {
        BoardScreenContent(
            state = BoardUiState(
                boardState = match,
            ),
            onAction = {},
            tileSize = tileSize,
            clickable = false
        )
    }
}