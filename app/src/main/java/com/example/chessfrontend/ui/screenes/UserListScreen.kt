package com.example.chessfrontend.ui.screenes

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chessfrontend.data.UserRepository
import com.example.chessfrontend.ui.components.MyCircularProfilePicture
import com.example.chessfrontend.ui.model.UserUiModel
import com.example.chessfrontend.ui.viewmodels.LoginAction
import com.example.chessfrontend.ui.viewmodels.UserListAction
import com.example.chessfrontend.ui.viewmodels.UserListState

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
    var searchQuery by remember { mutableStateOf("") }
    LazyColumn (
        Modifier.background(colorScheme.background)
    ) {
        item() { Spacer(Modifier.statusBarsPadding()) }
        item {
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            label = { Text("Search") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )
            }

        items(state.users.filter{ it.name.contains(searchQuery, ignoreCase = true) }) { user ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(125.dp)
                    .padding(8.dp)
            ) {
                Row (
                    modifier = Modifier
                        .weight(2f),
                    horizontalArrangement = Arrangement.Absolute.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    MyCircularProfilePicture(
                        modifier = Modifier
                            .padding(8.dp),
                        model = user.profilePicture
                    )
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .padding(8.dp)
                            .fillMaxHeight()
                    ) {
                        Text(
                            modifier = Modifier
                                .padding(4.dp),
                            text = user.name,
                            fontSize = 20.sp
                        )

                        Text(
                            modifier = Modifier
                                .padding(2.dp),
                            text = "Matches won: " + user.winedMatches.size,
                            fontSize = 14.sp
                        )

                        Text(
                            modifier = Modifier
                                .padding(2.dp),
                            text = "Matches lost: " + user.lostMatches.size,
                            fontSize = 14.sp
                        )
                    }


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
        item() { Spacer(Modifier.navigationBarsPadding()) }
    }
}

@Composable
@Preview(showBackground = true)
fun UserListScreenPreview() {
    UserListScreenContent(
        onAction = {},
        state = UserListState(
            users = listOf(
                UserUiModel(
                    id = 1,
                    name = "John Doe"
                )
            )
        )
    )
}