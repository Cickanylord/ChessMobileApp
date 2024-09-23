package com.example.chessfrontend.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.content.MediaType.Companion.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.chessfrontend.R
import com.example.chessfrontend.ui.viewmodels.BoardAction
import com.example.chessfrontend.ui.viewmodels.BoardUiState
import com.example.chessfrontend.ui.viewmodels.BoardViewModel
import com.hu.bme.aut.chess.ai_engine.board.BoardData
import com.hu.bme.aut.chess.ai_engine.board.pieces.enums.PieceColor
import com.hu.bme.aut.chess.ai_engine.board.pieces.enums.PieceName
import com.hu.bme.aut.chess.ai_engine.board.pieces.peice_interface.Piece



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

                                    state.board.getPiece(pos)?.let { piece ->
                                        onAction(BoardAction.PieceClicked(piece))
                                    }

                                    if(state.legalMoves.contains(pos)) {
                                        onAction(BoardAction.Step(pos))
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