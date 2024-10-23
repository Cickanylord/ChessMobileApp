package com.example.chessfrontend.ui.screenes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chessfrontend.ui.viewmodels.LoginAction
import com.example.chessfrontend.ui.viewmodels.UserListAction
import com.example.chessfrontend.ui.viewmodels.UserListState
import com.example.chessfrontend.ui.viewmodels.UserListUiModel
import com.example.chessfrontend.ui.viewmodels.UserListViewModel
import kotlin.reflect.KFunction1

@Composable
fun UserListScreenRoot(
    userListViewModel: UserListViewModel,
) {
    UserListScreenContent(
        state = userListViewModel.uiState,
        onAction = userListViewModel::handleAction
    )
}


@Composable
fun UserListScreenContent(
    state: UserListState,
    onAction: (UserListAction) -> Unit
) {
    LazyColumn {
        items(state.users.size) { index ->
            val user = state.users[index]
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(75.dp)
                    .padding(8.dp)
            ) {
                Row (
                    modifier = Modifier
                        .weight(2f),
                    horizontalArrangement = Arrangement.Absolute.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        modifier = Modifier
                            .padding(8.dp)
                            .weight(1f),
                        text = user.userName,
                        fontSize = 20.sp
                    )

                    IconButton(

                        onClick = {
                            onAction(UserListAction.AddFriend(user.id))
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Add,
                            contentDescription = "Add Friend"
                        )
                    }
                }
            }
        }

    }
}

@Composable
@Preview(showBackground = true)
fun UserListScreenPreview() {
    UserListScreenContent(
        onAction = {},
        state = UserListState(
            users = listOf(
                UserListUiModel(
                    id = 1,
                    userName = "John Doe"
                )
            )
        )
    )
}