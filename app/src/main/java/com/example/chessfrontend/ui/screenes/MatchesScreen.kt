package com.example.chessfrontend.ui.screenes

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.chessfrontend.ui.components.DisplayWinLoseRatio
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
        for(match in state.matches.filter { it.isGoing }) {
            item {
                MatchCard(
                    match = match,
                    onAction = onAction,
                    onClick = { onNavigationToMatch(match) }
                ) {
                    Column{
                        Text(
                            modifier = Modifier.padding(
                                start = 8.dp,
                                end = 8.dp,
                            ),
                            text = state.partner?.name ?: "ERROR"
                        )
                        Text(
                            modifier = Modifier.padding(
                                start = 8.dp,
                                end = 8.dp
                            ),
                            text = "stats:",
                            fontWeight = FontWeight.ExtraLight,
                            fontSize = 10.sp
                        )
                        DisplayWinLoseRatio(ratio = state.winLoseRatio)
                    }
                }
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
                listOf(),
                listOf(),
                listOf()
            ),
            matches = listOf(
                MatchUiModel()
            )
        ),
        onAction = {}
    )
}