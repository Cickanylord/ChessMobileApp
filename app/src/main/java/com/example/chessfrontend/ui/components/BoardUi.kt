package com.example.chessfrontend.ui.components

import ai_engine.board.pieces.Piece
import ai_engine.board.pieces.enums.PieceColor
import ai_engine.board.pieces.enums.PieceName
import android.annotation.SuppressLint
import android.media.MediaPlayer
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.chessfrontend.R
import com.example.chessfrontend.ui.model.MatchUiModel
import com.example.chessfrontend.ui.viewmodels.gameModes.BoardAction
import com.example.chessfrontend.ui.viewmodels.gameModes.BoardUiState
import com.example.chessfrontend.ui.viewmodels.gameModes.BoardViewModelImpl
import kotlinx.coroutines.delay

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun BoardScreenRoot(
    viewModel: BoardViewModelImpl,
) {
    Scaffold {
        BoardScreenContent(
            state = viewModel.uiState,
            onAction = viewModel::handleAction
        )
    }
}

@Composable
fun BoardScreenContent(
    state: BoardUiState,
    onAction: (BoardAction) -> Unit,
    tileSize: Int = 45,
    clickable: Boolean = true
) {
    val rotation: Float = if(state.boardState.challenged == state.user?.id && state.user.id != -1L) 180f else 0f

    if (state.playMoveSound) {
        PlaySoundEffect(
            resId = R.raw.move,
            onCompletion = { onAction(BoardAction.OnStepSoundOver) }
        )
    }


    Box(
        modifier = Modifier
            .wrapContentSize()
            .graphicsLayer { rotationZ = rotation },

    ) {
        Column {
            for (i in 0 until 8) {
                Row {
                    for (j in 0 until 8) {
                        var gridcolor = Color(0xff769656)
                        if ((i + j) % 2 == 0) {
                            gridcolor = Color(0xffeeeedd)
                        }
                        Box(
                            modifier = Modifier
                                .size(tileSize.dp)
                                .background(gridcolor)
                                .clickable(enabled = clickable) {
                                    val pos = Pair(i, j)

                                    if (state.legalMoves.contains(pos)) {
                                        onAction(BoardAction.Step(pos))
                                    } else {

                                        state.board
                                            .getPiece(pos)
                                            ?.let { piece ->
                                                onAction(BoardAction.PieceClicked(piece))
                                            }
                                    }
                                }
                        ) {
                            state.board.getPiece(i, j)?.let { piece ->
                                DrawPiece(
                                    imageID = piece.getPieceImage(),
                                    rotation = rotation
                                )
                            }

                            if (state.legalMoves.contains(Pair(i, j))) {
                                DrawCircle()
                            }
                        }
                    }
                }
            }
        }
        if (clickable) {
            if (state.boardState.loser == state.user?.id && state.boardState.isGoing.not()) {
                Image(
                    painter = painterResource(id = R.drawable.you_died),
                    contentDescription = "You Lost",
                    modifier = Modifier
                        .fillMaxSize()
                        .alpha(0.8f)
                        .graphicsLayer { rotationZ = rotation }
                )
                PlaySoundEffect(R.raw.lose)
            } else if (state.boardState.isGoing.not()) {
                Image(
                    painter = painterResource(id = R.drawable.humanity_restored),
                    contentDescription = "You Lost",
                    modifier = Modifier
                        .fillMaxSize()
                        .alpha(0.8f)
                        .graphicsLayer { rotationZ = rotation }
                )
                PlaySoundEffect(R.raw.win)
            }
        }
    }
}

@Composable
fun PlaySoundEffect(
    resId: Int,
    onCompletion: () -> Unit = {}
) {
    val context = LocalContext.current
    val mediaPlayer = remember { MediaPlayer.create(context, resId) }

    LaunchedEffect(key1 = Unit) {
        mediaPlayer.start()
    }

    DisposableEffect(mediaPlayer) {
        mediaPlayer.setOnCompletionListener {
            onCompletion()
        }
        onDispose {
            mediaPlayer.release()
        }
    }
}

fun Piece.getPieceImage(): Int {
    return when (this.name) {
        PieceName.KING -> if (this.pieceColor == PieceColor.WHITE) R.drawable.white_king else R.drawable.black_king
        PieceName.QUEEN -> if (this.pieceColor == PieceColor.WHITE) R.drawable.white_queen else R.drawable.black_queen
        PieceName.ROOK -> if (this.pieceColor == PieceColor.WHITE) R.drawable.white_rook else R.drawable.black_rook
        PieceName.BISHOP -> if (this.pieceColor == PieceColor.WHITE) R.drawable.white_bishop else R.drawable.black_bishop
        PieceName.KNIGHT -> if (this.pieceColor == PieceColor.WHITE) R.drawable.white_knight else R.drawable.black_knight
        PieceName.PAWN -> if (this.pieceColor == PieceColor.WHITE) R.drawable.white_pawn else R.drawable.black_pawn
        else -> throw IllegalArgumentException("invalid piece name")
    }
}



@SuppressLint("UnrememberedMutableState")
@Preview(showBackground = true)
@Composable
fun BoardScreenPreview() {
 BoardScreenContent(
     state = BoardUiState(
         boardState = MatchUiModel(),
         legalMoves = listOf(Pair(5, 1), Pair(4, 1)),
         clickedPiece = null
     ),
     onAction = {}
 )
}