package com.example.chessfrontend.ui.screenes

import ai_engine.board.BoardData
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.chessfrontend.ui.components.MatchCard
import com.example.chessfrontend.ui.model.MatchUiModel
import com.example.chessfrontend.ui.model.UserUiModel
import com.example.chessfrontend.ui.viewmodels.MatchesAction
import com.example.chessfrontend.ui.viewmodels.MatchesUiState
import com.example.chessfrontend.ui.viewmodels.MatchesViewModel

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