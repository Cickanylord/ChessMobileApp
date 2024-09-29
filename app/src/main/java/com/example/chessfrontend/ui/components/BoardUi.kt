package com.example.chessfrontend.ui.components

import ai_engine.board.pieces.Piece
import ai_engine.board.pieces.enums.PieceColor
import ai_engine.board.pieces.enums.PieceName
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.auth.bme.chess.ai_engine.board.BoardData
import com.example.chessfrontend.R
import com.example.chessfrontend.ui.viewmodels.gameModes.BoardAction
import com.example.chessfrontend.ui.viewmodels.gameModes.BoardUiState
import com.example.chessfrontend.ui.viewmodels.gameModes.BoardViewModel

@Composable
fun BoardScreenRoot(
    viewModel: BoardViewModel,
) {
    BoardScreenContent(
        state = viewModel.uiState,
        onAction = viewModel::handleAction
    )
}

@Composable
private fun BoardScreenContent(
    state: BoardUiState,
    onAction: (BoardAction) -> Unit,
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
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
                                .size(45.dp)
                                .background(gridcolor)
                                .clickable {
                                    val pos = Pair(i,j)

                                    if(state.legalMoves.contains(pos)) {
                                        onAction(BoardAction.Step(pos))
                                    } else {
                                        state.board.getPiece(pos)?.let { piece ->
                                            onAction(BoardAction.PieceClicked(piece))
                                        }
                                    }
                                }
                        ) {
                            state.board.getPiece(i, j)?.let { piece ->
                                DrawPiece(piece.getPieceImage())
                            }

                            if (state.legalMoves.contains(Pair(i, j))) {
                                DrawCircle()
                            }
                        }
                    }
                }
            }
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



@Preview(showBackground = true)
@Composable
fun BoardScreenPreview() {
 BoardScreenContent(
     state = BoardUiState(
         board = BoardData("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1"),
         legalMoves = listOf(Pair(5, 1), Pair(4, 1)),
         clickedPiece = null
     ),
     onAction = {}
 )
}