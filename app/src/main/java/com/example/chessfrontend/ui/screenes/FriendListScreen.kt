package com.example.chessfrontend.ui.screenes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.chessfrontend.R
import com.example.chessfrontend.data.model.User
import com.example.chessfrontend.ui.viewmodels.FriendListUiState
import com.example.chessfrontend.ui.viewmodels.FriendListViewModel


@Composable
fun FriendListScreenRoot(
    friendliestViewModel: FriendListViewModel
) {
    FriendListScreenContent(
        state = friendliestViewModel.uiState,
    )
}

@Composable
fun FriendListScreenContent(
    state: FriendListUiState
) {
    LazyColumn {
        items(state.friends) { item ->
            Card(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .weight(8f)
                    ,
                    horizontalArrangement = Arrangement.Absolute.Center,
                    verticalAlignment = Alignment.CenterVertically // Add this for vertical centering
                ) {
                    Text(
                        modifier = Modifier.weight(2f),
                        text = item.name
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    // Game button
                    IconButton(
                        onClick = {  },
                        modifier = Modifier
                            .size(ButtonSize)
                            .weight(2f)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_play_arrow_24),
                            contentDescription = "Start Game"
                        )
                    }

                    Spacer(modifier = Modifier.weight(1f))

                    // Chat button
                    IconButton(
                        onClick = {

                        },
                        modifier = Modifier
                            .size(ButtonSize)
                            .weight(2f)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_chat_24),
                            contentDescription = "Chat"
                        )
                    }
                }
            }
        }
    }
}
private val ButtonSize = 60.dp

@Preview(showBackground = true)
@Composable
fun FriendListScreenContentPreview() {
    FriendListScreenContent(
        state = FriendListUiState(
            friends = listOf(
                User(1, "friend1", listOf(), listOf(), listOf(), listOf(), listOf(), listOf()),
                User(2, "friend2", listOf(), listOf(), listOf(), listOf(), listOf(), listOf()),
                User(3, "friend3", listOf(), listOf(), listOf(), listOf(), listOf(), listOf())
            )
        )
    )
}


