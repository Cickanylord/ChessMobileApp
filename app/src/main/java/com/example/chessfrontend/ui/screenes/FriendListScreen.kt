package com.example.chessfrontend.ui.screenes

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.chessfrontend.ui.viewmodels.FriendListUiState
import com.example.chessfrontend.ui.viewmodels.FriendListViewModel


@Composable
fun FriendListScreenRoot(
    friendliestViewModel: FriendListViewModel
) {
    FriendListScreenContent(
        sate = friendliestViewModel.uiState,
    )
}

@Composable
fun FriendListScreenContent(
    sate: FriendListUiState
) {
    LazyColumn {
        items(sate.friends) { item ->
            Card(modifier = Modifier.padding(8.dp)) {
                Text(text = item.name, modifier = Modifier.padding(16.dp))
            }
            Divider()
        }
    }
}



