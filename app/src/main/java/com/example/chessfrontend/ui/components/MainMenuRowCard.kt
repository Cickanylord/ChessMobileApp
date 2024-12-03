package com.example.chessfrontend.ui.components

import ai_engine.board.BoardData
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chessfrontend.ui.model.MatchUiModel


@Composable
fun MainMenuRowCard(
    gameModeText: Int,
    gameModeDescription: Int,
    gameModeImage: Int,
    gameDescription: BoardData,
    onClick: () -> Unit = {},
) {
    Box (
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clickable { onClick() }
    ) {
        DrawMatchRow(
            match = MatchUiModel(board = gameDescription)

        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        top = 8.dp
                    ),
                contentAlignment = Alignment.TopStart
            ) {
                Column {
                    Text(
                        text = stringResource(gameModeText),
                        fontWeight = FontWeight.Bold,
                        fontSize = 17.sp,
                        maxLines = 1
                    )

                    Text(
                        text = stringResource(gameModeDescription),
                        fontWeight = FontWeight.Thin,
                        fontSize = 12.sp,
                        maxLines = 1
                    )
                }

                Box(
                    modifier = Modifier
                        .width(40.dp)
                        .height(40.dp)
                        .align(Alignment.BottomStart),
                    contentAlignment = Alignment.Center
                ) {
                    DrawPiece(gameModeImage)
                }
            }
        }
    }
}
