package com.example.chessfrontend.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.chessfrontend.R
import com.example.chessfrontend.ui.model.UserUiModel
import com.example.chessfrontend.ui.viewmodels.ProfileAction
import com.example.chessfrontend.ui.viewmodels.ProfileUiState

@Composable
fun InteractionBar(
    state: ProfileUiState,
    onNavigationToChat: (UserUiModel) -> Unit,
    onAction: (ProfileAction) -> Unit
) {
    Row (
        modifier = Modifier
            .height(100.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        MyIconButtonWithSmallText(
            text = "Messages",
            onClick = { onNavigationToChat(state.user) },
            icon = painterResource(id = R.drawable.baseline_chat_24),
            modifier = Modifier.weight(3f)
        )

        Spacer(Modifier.weight(1f))

        MyIconButtonWithSmallText(
            text = "Play Game",
            onClick = { onAction(ProfileAction.PostMatch) },
            icon = painterResource(id = R.drawable.baseline_play_arrow_24),
            modifier = Modifier.weight(3f)
        )
    }
}