package com.example.chessfrontend.ui.screenes

import ai_engine.board.BoardData
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.chessfrontend.R
import com.example.chessfrontend.ui.components.DrawMatchRow
import com.example.chessfrontend.ui.components.DrawPiece
import com.example.chessfrontend.ui.components.MyIconButtonWithSmallText
import com.example.chessfrontend.ui.components.MyMainMenuDrawer
import com.example.chessfrontend.ui.components.MyRectangularProfilePicture
import com.example.chessfrontend.ui.model.MatchUiModel
import com.example.chessfrontend.ui.model.UserUiModel
import com.example.chessfrontend.ui.viewmodels.MainMenuAction
import com.example.chessfrontend.ui.viewmodels.MainMenuState
import com.example.chessfrontend.ui.viewmodels.MainMenuViewModel
import kotlinx.coroutines.CoroutineStart
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

@Composable
fun MainMenuRoot(
    mainMenuViewModel: MainMenuViewModel,
    onNavigationToProfile: (UserUiModel) -> Unit,
    onNavigationToOnlineGame: (Pair<Long, Long>) -> Unit,
    onNavigationToOfflineGame: () -> Unit,
    onNavigationToAiGame: () -> Unit
) {
    MainMenuContent(
        onNavigationToProfile = onNavigationToProfile,
        onNavigationToOnlineGame = onNavigationToOnlineGame,
        onNavigationToOfflineGame = onNavigationToOfflineGame,
        onNavigationToAiGame = onNavigationToAiGame,
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
    state: MainMenuState,
    onAction: (MainMenuAction) -> Unit
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)


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
                    )

                }
            ) { padding ->
                MainMenuBody(
                    onNavigationToOnlineGame = onNavigationToOnlineGame,
                    onNavigationToOfflineGame = onNavigationToOfflineGame,
                    onNavigationToAiGame = onNavigationToAiGame,
                    padding = padding,
                    state = state,
                )

            }
        }
    )
}

@Composable
fun MyTopBar(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    user: UserUiModel,
    text: String = "",
) {
    Column {
        Spacer(Modifier.windowInsetsPadding(WindowInsets.statusBars))
        Row(
            modifier = modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {


            MyRectangularProfilePicture(
                user = user,
                onClick = onClick,
            )


            Text(
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(start = 8.dp),
                fontSize = MaterialTheme.typography.titleLarge.fontSize,
                fontWeight = MaterialTheme.typography.titleLarge.fontWeight,
                text = text
            )


            MyIconButtonWithSmallText(
                modifier = Modifier.weight(2f),
                text = "",
                onClick = {},
                icon = painterResource(id = R.drawable.baseline_play_arrow_24),
            )
        }
    }
}

@Composable
fun MainMenuBody(
    onNavigationToOnlineGame: (Pair<Long, Long>) -> Unit,
    onNavigationToOfflineGame: () -> Unit = {},
    onNavigationToAiGame: () -> Unit = {},
    padding: PaddingValues,
    state: MainMenuState
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
                onClick = { onNavigationToOnlineGame(Pair(state.continueMatchBoard.id, findPartnerId(state.user!!, state.continueMatchBoard))) }
            )


            MainMenuRowCard(
                gameModeText = R.string.play_online,
                gameModeDescription = R.string.vs_random,
                gameModeImage = if (isSystemInDarkTheme()) R.drawable.white_bishop else R.drawable.black_bishop,
                gameDescription = state.onlineBoard.board,
                onClick = { TODO() }
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

fun findPartnerId(
    user: UserUiModel,
    match: MatchUiModel
): Long =
    if (user.id == match.challenged) {
        match.challenger

    } else {
        match.challenged
    }


@Composable
fun MainMenuRowCard(
    gameModeText: Int,
    gameModeDescription: Int,
    gameModeImage: Int,
    gameDescription: BoardData,
    onClick: () -> Unit = {},
) {
    Box (
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clickable { onClick() }
    ) {
        DrawMatchRow(
            match = MatchUiModel(board = gameDescription)

        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        top = 8.dp
                    ),
                contentAlignment = Alignment.TopStart
            ) {
                Column {
                    Text(
                        text = stringResource(gameModeText),
                        fontWeight = FontWeight.Bold,
                        fontSize = 17.sp,
                        maxLines = 1
                    )

                    Text(
                        text = stringResource(gameModeDescription),
                        fontWeight = FontWeight.Thin,
                        fontSize = 12.sp,
                        maxLines = 1
                    )
                }

                Box(
                    modifier = Modifier
                        .width(40.dp)
                        .height(40.dp)
                        .align(Alignment.BottomStart),
                    contentAlignment = Alignment.Center
                ) {
                    DrawPiece(gameModeImage)
                }
            }
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
        onNavigationToAiGame = {}
    )
}

