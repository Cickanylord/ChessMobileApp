package com.example.chessfrontend.ui.screenes

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.chessfrontend.ui.components.BoardScreenContent
import com.example.chessfrontend.ui.model.UserUiModel
import com.example.chessfrontend.ui.viewmodels.ChatAction
import com.example.chessfrontend.ui.viewmodels.ChatUiState
import com.example.chessfrontend.ui.viewmodels.ChatViewModel
import com.example.chessfrontend.ui.viewmodels.gameModes.BoardAction
import com.example.chessfrontend.ui.viewmodels.gameModes.BoardUiState
import com.example.chessfrontend.ui.viewmodels.gameModes.OnlineBoardViewModelImpl

@Composable
fun OnlineBoardScreenRoot(
    boardViewModel: OnlineBoardViewModelImpl,
    onNavigationToProfile: (UserUiModel) -> Unit,
    chatViewModel: ChatViewModel
) {
    OnlineBoardScreenContent(
        boardState = boardViewModel.uiState,
        chatState = chatViewModel.uiState,
        onNavigationToProfile = onNavigationToProfile,
        onBoardAction = boardViewModel::handleAction,
        onChatAction = chatViewModel::handleAction
    )
}

@Composable
fun OnlineBoardScreenContent(
    boardState: BoardUiState,
    chatState: ChatUiState,
    onNavigationToProfile: (UserUiModel) -> Unit,
    onBoardAction: (BoardAction) -> Unit,
    onChatAction: (ChatAction) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize(),

        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        MyTopBar(
            onClick = {onNavigationToProfile(chatState.friend)},
            text = chatState.friend.name,
        )

        Box(
            modifier = Modifier
                .border(
                    width = 2.dp,
                    color = MaterialTheme.colorScheme.secondary.copy(alpha = 0.5f),
                    shape = RoundedCornerShape(8.dp)
                )
                .clip(RoundedCornerShape(8.dp))


        ) {
            BoardScreenContent(
                state = boardState,
                onAction = onBoardAction
            )
        }

        ChatScreenContent(
            state = chatState,
            onNavigationToProfile = {},
            onAction = onChatAction,
            displayTopBar = false
        )
    }
}

@Preview(showBackground = true)
@Composable
fun OnlineBoardScreenPreview() {
    OnlineBoardScreenContent(
        boardState = BoardUiState(),
        chatState = ChatUiState(),
        onNavigationToProfile = {},
        onBoardAction = {},
        onChatAction = {}
    )
}