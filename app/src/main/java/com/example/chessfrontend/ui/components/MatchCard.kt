package com.example.chessfrontend.ui.components

import ai_engine.board.BoardData
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.chessfrontend.ui.model.MatchUiModel
import com.example.chessfrontend.ui.viewmodels.MatchesAction

@Composable
fun MatchCard(
    match: MatchUiModel,
    onAction: (MatchesAction) -> Unit,
    onNavigationToMatch: (match: MatchUiModel) -> Unit = {},
    tileSize: Int = 20,
    content : @Composable () -> Unit = {}
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable(onClick = {onNavigationToMatch(match)})
        ,
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(tileSize.dp * 4)
        ) {
            DrawSmallBoard(
                tileSize = tileSize,
                match = match
            )

            Box() {
                content()
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun MatchCardPreview() {
    MatchCard(
        match = MatchUiModel(
            id = 1L,
            challenger = 1L,
            challenged = 1L,
            board = BoardData("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1"),
        ),
        onAction = {},
    )
}