package com.example.chessfrontend.ui.screenes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chessfrontend.R
import com.example.chessfrontend.ui.components.DisplayWinLoseRatio
import com.example.chessfrontend.ui.components.InteractionBar
import com.example.chessfrontend.ui.components.MatchCard
import com.example.chessfrontend.ui.components.ProfileHeader
import com.example.chessfrontend.ui.model.MatchUiModel
import com.example.chessfrontend.ui.model.UserUiModel
import com.example.chessfrontend.ui.viewmodels.ProfileAction
import com.example.chessfrontend.ui.viewmodels.ProfileUiState
import com.example.chessfrontend.ui.viewmodels.ProfileViewModel



@Composable
fun ProfileScreenRoot(
    onNavigationToChat: (UserUiModel) -> Unit,
    onNavigationToOnlineGame: (Pair<Long, Long>) -> Unit,
    onNavigationToProfilePictureUpload: () -> Unit,
    profileViewModel: ProfileViewModel,
) {
    ProfileScreen(
        onNavigationToChat = onNavigationToChat,
        onNavigationToOnlineGame = onNavigationToOnlineGame,
        onNavigationToProfilePictureUpload = onNavigationToProfilePictureUpload,
        state = profileViewModel.uiState,
        onAction = profileViewModel::handleAction
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    onNavigationToChat: (UserUiModel) -> Unit,
    onNavigationToOnlineGame: (Pair<Long, Long>) -> Unit,
    onNavigationToProfilePictureUpload: () -> Unit,
    state: ProfileUiState,
    onAction: (ProfileAction) -> Unit
) {


    Spacer(Modifier.windowInsetsTopHeight(WindowInsets.statusBars))
    Scaffold (
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
        ) {
            // Profile Header
            item {
                ProfileHeader(
                    state = state,
                    onCLick = {
                        onNavigationToProfilePictureUpload()
                    }
                )
            }

            item {
                if (!state.isMyProfile) {
                    InteractionBar(
                        state = state,
                        onNavigationToChat = onNavigationToChat,
                        onAction = onAction
                    )
                }
            }

            // Going Matches Section
            item {
                ProfileScreenSeparator(stringResource(id = R.string.going_matches))
            }
            items(
                state.matches.filter { it.isGoing }

            ) { match ->
                MatchCard(
                    match = match,
                    onAction = {},
                    onClick = {onNavigationToOnlineGame(Pair(match.id, findPartnerId(state.myProfile, match)))}
                ) {
                    ProfileMatchDescription(
                        state = state,
                        match = match
                    )
                }
            }

            // Ended Matches Section
            item {
                ProfileScreenSeparator(stringResource(id = R.string.ended_matches))
            }
            items(state.matches.filter { !it.isGoing }) { match ->
                MatchCard(
                    match = match,
                    onAction = {},
                    onClick = {}
                ) {
                    ProfileMatchDescription(
                        state = state,
                        match = match
                    )
                }
            }
        }
    }
}

@Composable
fun ProfileMatchDescription(
    state: ProfileUiState,
    match: MatchUiModel

) {
    Column(
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        if(match.isGoing.not()) {
            Text(
                modifier = Modifier.padding(
                    start = 8.dp,
                    end = 8.dp,
                ),
                text = "Winner:",
                fontWeight = FontWeight.Bold,
                fontSize = 10.sp
            )

            Text(
                modifier = Modifier.padding(
                    start = 8.dp,
                    end = 8.dp,
                ),
                text = state.users.find { it.id == match.winner }?.name ?: "Unknown",
            )
        }
        Spacer(modifier = Modifier.weight(1f))

        Text(
            modifier = Modifier
                .padding(
                    start = 8.dp,
                    end = 8.dp,
                ),
            text = "${state.users.find { it.id == match.challenger }?.name} vs ${state.users.find { it.id == match.challenged }?.name}"
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
        DisplayWinLoseRatio(state.winLoseRatio)
    }
}




@Composable
fun ProfileScreenSeparator(text: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            fontSize = MaterialTheme.typography.titleLarge.fontSize

        )
    }
}


@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview(){
    ProfileScreen(
        onNavigationToChat = {},
        onNavigationToOnlineGame = {},
        state = ProfileUiState(
            user = UserUiModel(
                name = "John Doe"
            ),
            matches = listOf(
                MatchUiModel(isGoing = false),
                MatchUiModel(isGoing = false),
                MatchUiModel(isGoing = false),
            )
        ),
        onNavigationToProfilePictureUpload = {},
        onAction = {}
    )
}