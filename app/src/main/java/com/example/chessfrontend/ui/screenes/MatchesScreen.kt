package com.example.chessfrontend.ui.screenes

import ai_engine.board.BoardData
import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.chessfrontend.R
import com.example.chessfrontend.ui.components.BoardScreenContent
import com.example.chessfrontend.ui.model.MatchUiModel
import com.example.chessfrontend.ui.model.UserUiModel
import com.example.chessfrontend.ui.viewmodels.MatchesAction
import com.example.chessfrontend.ui.viewmodels.MatchesUiState
import com.example.chessfrontend.ui.viewmodels.MatchesViewModel
import com.example.chessfrontend.ui.viewmodels.gameModes.BoardUiState

@Composable
fun MatchesScreenRoot(
    matchesViewModel: MatchesViewModel = hiltViewModel(),
    onNavigationToMatch: (match: MatchUiModel) -> Unit = {}
) {
    MatchesScreenContent(
        state = matchesViewModel.uiState,
        onAction = matchesViewModel::handleAction,
        onNavigationToMatch = onNavigationToMatch
    )
}

@Composable
fun MatchesScreenContent(
    state: MatchesUiState,
    onAction: (MatchesAction) -> Unit,
    onNavigationToMatch: (match: MatchUiModel) -> Unit = {}
) {
    LazyColumn() {
        for(match in state.matches) {
            item {
                MatchCard(
                    match = match,
                    onAction = onAction,
                    onNavigationToMatch = onNavigationToMatch
                )
            }
        }
    }
}

@SuppressLint("UnrememberedMutableState")
@Composable
fun MatchCard(
    match: MatchUiModel,
    onAction: (MatchesAction) -> Unit,
    onNavigationToMatch: (match: MatchUiModel) -> Unit = {},
    tileSize: Int = 20
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(tileSize.dp * 4)
        ) {
            Box(
                modifier = Modifier
                    .size(8.dp * tileSize)
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

            IconButton(
                onClick = {onNavigationToMatch(match)},
                modifier = Modifier
                    .align(Alignment.CenterVertically)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_play_arrow_24),
                    contentDescription = "Start Game"
                )
            }
        }
    }
}


@Composable
@Preview(showBackground = true)
fun MatchesScreenPreview() {
    MatchesScreenContent(
        state = MatchesUiState(
            partner = UserUiModel(
                id = 1,
                name = "friend1",
                listOf(),
                listOf(),
                listOf(),
                listOf(),
                listOf(),
                listOf()
            ),
            matches = listOf(
                MatchUiModel(
                    id = 1L,
                    challenger = 1L,
                    challenged = 1L,
                    board = BoardData("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1")
                )
            )
        ),
        onAction = {}
    )
}