package com.example.chessfrontend.ui.screenes

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.example.chessfrontend.R
import com.example.chessfrontend.ui.components.MainMenuRowCard
import com.example.chessfrontend.ui.components.MyIconButtonWithSmallText
import com.example.chessfrontend.ui.components.MyMainMenuDrawer
import com.example.chessfrontend.ui.components.MyToast
import com.example.chessfrontend.ui.components.MyTopBar
import com.example.chessfrontend.ui.model.UserUiModel
import com.example.chessfrontend.ui.viewmodels.MainMenuAction
import com.example.chessfrontend.ui.viewmodels.MainMenuState
import com.example.chessfrontend.ui.viewmodels.MainMenuViewModel
import com.example.chessfrontend.util.findPartnerId
import dagger.hilt.android.internal.Contexts

@Composable
fun MainMenuRoot(
    mainMenuViewModel: MainMenuViewModel,
    onNavigationToProfile: (UserUiModel) -> Unit,
    onNavigationToOnlineGame: (Pair<Long, Long>) -> Unit,
    onNavigationToOfflineGame: () -> Unit,
    onNavigationToAiGame: () -> Unit,
    onNavigationToAddFriend: () -> Unit,
    onLogOut: () -> Unit
) {
    MainMenuContent(
        onNavigationToProfile = onNavigationToProfile,
        onNavigationToOnlineGame = onNavigationToOnlineGame,
        onNavigationToOfflineGame = onNavigationToOfflineGame,
        onNavigationToAiGame = onNavigationToAiGame,
        onNavigationToAddFriend = onNavigationToAddFriend,
        onLogOut = onLogOut,
        state = mainMenuViewModel.uiState,
        onAction = mainMenuViewModel::handleAction
    )
}

@Composable
fun MainMenuContent(
    onNavigationToProfile: (UserUiModel) -> Unit,
    onNavigationToOnlineGame: (Pair<Long, Long>) -> Unit,
    onNavigationToOfflineGame: () -> Unit,
    onNavigationToAiGame: () -> Unit,
    onNavigationToAddFriend: () -> Unit,
    onLogOut: () -> Unit,
    state: MainMenuState,
    onAction: (MainMenuAction) -> Unit
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    if (state.newMatch.id != -1L) {
        onNavigationToOnlineGame(
            Pair(
                state.newMatch.id,
                findPartnerId(state.user!!,  state.newMatch)
            )
        )
        onAction(MainMenuAction.RemoveNewGame)
    }


    if (state.drawerState == DrawerValue.Open)  {
        LaunchedEffect(Unit) {
            try {
                drawerState.open()
            } finally {
                onAction(MainMenuAction.CloseDrawer)
            }
        }
    }

    ModalNavigationDrawer(

        drawerState = drawerState,
        drawerContent = {
            MyMainMenuDrawer(
                state = state,
                onNavigationToProfile = onNavigationToProfile,
                onAction = onAction
            )
        },
        content = {
            Scaffold(
                modifier = Modifier
                   ,
                topBar = {
                    MyTopBar(
                        onClick = {
                            onAction(MainMenuAction.OpenDrawer)
                        },
                        user = state.user ?: UserUiModel(),
                        text = state.user?.name ?: "",
                    ) {
                        Box {
                            DropdownMenu(
                                expanded = state.dropDownMenuOpen,
                                onDismissRequest = { onAction(MainMenuAction.ToggleDropDownMenu) }
                            ) {
                                DropdownMenuItem(
                                    text = { Text("Add Friend") },
                                    onClick = {
                                        onAction(MainMenuAction.ToggleDropDownMenu)
                                        onNavigationToAddFriend()
                                    }
                                )
                                DropdownMenuItem(
                                    text = { Text("Log Out") },
                                    onClick = {
                                        onAction(MainMenuAction.ToggleDropDownMenu)
                                        onAction(MainMenuAction.LogOut)
                                        onLogOut()
                                    }
                                )
                            }
                            MyIconButtonWithSmallText(
                                text = "",
                                onClick = { onAction(MainMenuAction.ToggleDropDownMenu)  },
                                icon = rememberVectorPainter(Icons.Filled.MoreVert),
                            )
                        }
                    }
                }
            ) { padding ->
                MainMenuBody(
                    onNavigationToOnlineGame = onNavigationToOnlineGame,
                    onNavigationToOfflineGame = onNavigationToOfflineGame,
                    onNavigationToAiGame = onNavigationToAiGame,
                    padding = padding,
                    state = state,
                    onAction = onAction
                )

            }
        }
    )
}

@Composable
fun MainMenuBody(
    onNavigationToOnlineGame: (Pair<Long, Long>) -> Unit,
    onNavigationToOfflineGame: () -> Unit = {},
    onNavigationToAiGame: () -> Unit = {},
    padding: PaddingValues,
    state: MainMenuState,
    onAction: (MainMenuAction) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        item {
            MainMenuRowCard(
                gameModeText = R.string.Continue_Game,
                gameModeDescription = R.string.vs_your_friend,
                gameModeImage = if (isSystemInDarkTheme()) R.drawable.white_rook else R.drawable.black_rook,
                gameDescription = state.continueMatchBoard.board,
                onClick = {
                    if(state.continueMatchBoard.id == -1L) {
                        onAction(MainMenuAction.LoadData)
                    }
                    if (state.continueMatchBoard.id != -1L) {
                        onNavigationToOnlineGame(
                            Pair(
                                state.continueMatchBoard.id,
                                findPartnerId(state.user!!, state.continueMatchBoard)
                            )
                        )
                    }
                }
            )


            MainMenuRowCard(
                gameModeText = R.string.play_online,
                gameModeDescription = R.string.vs_random,
                gameModeImage = if (isSystemInDarkTheme()) R.drawable.white_bishop else R.drawable.black_bishop,
                gameDescription = state.onlineBoard.board,
                onClick = {
                    onAction(MainMenuAction.AddNewGame)
                }
            )


            MainMenuRowCard(
                gameModeText = R.string.play_against_computer,
                gameModeDescription = R.string.vs_Chess_GPT,
                gameModeImage = if (isSystemInDarkTheme()) R.drawable.white_queen else R.drawable.black_queen,
                gameDescription = state.aiBoard.board,
                onClick = { onNavigationToAiGame() }
            )


            MainMenuRowCard(
                gameModeText = R.string.Play_locally,
                gameModeDescription = R.string.vs_your_friend,
                gameModeImage = if (isSystemInDarkTheme()) R.drawable.white_king else R.drawable.black_king,
                gameDescription = state.offlineBoard.board,
                onClick = { onNavigationToOfflineGame() }
            )
        }
    }
}

@Preview(
    showBackground = true,
)
@Composable
fun MainMenuContentPreview() {
    MainMenuContent(
        state = MainMenuState(),
        onAction = {},
        onNavigationToProfile = {},
        onNavigationToOnlineGame = {},
        onNavigationToOfflineGame = {},
        onNavigationToAiGame = {},
        onNavigationToAddFriend = {},
        onLogOut = {}
    )
}

