package com.example.chessfrontend.ui.viewmodels.gameModes

import ai_engine.board.pieces.PieceFactory
import ai_engine.board.pieces.enums.PieceName
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.chessfrontend.data.MatchRepository
import com.example.chessfrontend.data.UserRepository
import com.example.chessfrontend.data.localStorage.UserPreferencesRepository
import com.example.chessfrontend.data.model.StepRequestEntity
import com.example.chessfrontend.data.netwrok.ChessApiService
import com.example.chessfrontend.ui.model.toUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnlineBoardViewModelImpl @Inject constructor(
    private val chessApiService: ChessApiService,
    private val userPreferencesRepository: UserPreferencesRepository,
    private val matchRepository: MatchRepository,
    private val userRepository: UserRepository,
    savedStateHandle: SavedStateHandle
) : BoardViewModelImpl() {

    init {
        val matchId = savedStateHandle.get<Long>("matchId")!!

        viewModelScope.launch {
            matchRepository.reOpenSocket()

            uiState = uiState.copy(
                matchId = matchId
            )
            loadBoard()
            userPreferencesRepository.saveLasOnlineGame(uiState.matchId)
        }
    }

    override fun loadBoard() {
        viewModelScope.launch {
            matchRepository.getMatch(uiState.matchId)
            observeMatches()
            observeUsers()
        }
    }

    override fun onPromotion(pieceName: PieceName) {
        uiState.boardState.board.addPiece(
            PieceFactory.makePiece(
                pieceName,
                uiState.clickedPiece!!.pieceColor,
                uiState.clickedPiece!!.position
            )
        )
        uiState = uiState.copy(
            isWhitePromoting = false,
            isBlackPromoting = false,
        )

        viewModelScope.launch {
            matchRepository.step(
                StepRequestEntity(
                    matchId = uiState.matchId,
                    board =  uiState.boardState.board.toString(),
                )
            )
        }


    }

    override fun step(move: Pair<Int, Int>) {
        val queryBoard = uiState.board
        queryBoard
            .boardLogic
            .move(queryBoard.getPiece(uiState.clickedPiece!!.position), move)

        val isWhitePromoting = queryBoard.board[0].any{ it.piece?.name == PieceName.PAWN }
        val isBlackPromoting = queryBoard.board[7].any{ it.piece?.name == PieceName.PAWN }

        if(!isWhitePromoting && !isBlackPromoting) {
            viewModelScope.launch {
                matchRepository.step(
                    StepRequestEntity(
                        matchId = uiState.matchId,
                        board = queryBoard.toString(),
                    )
                )
            }
        } else {
            uiState = uiState.copy(
                isWhitePromoting = isWhitePromoting,
                isBlackPromoting = isBlackPromoting,
                legalMoves = listOf()
            )
        }
    }

    private fun observeMatches() {
        viewModelScope.launch {
            matchRepository.matches.observeForever() { matches ->
                val currentMatch = matches.find { it.id == uiState.matchId }?.toUiModel()!!

                uiState = uiState.copy(
                    boardState = currentMatch,

                    legalMoves = listOf(),
                    playMoveSound = currentMatch.board.toString() == uiState.board.toString(),
                    isWhitePromoting = currentMatch.board.board[0].any{ it.piece?.name == PieceName.PAWN },
                    isBlackPromoting = currentMatch.board.board[7].any{ it.piece?.name == PieceName.PAWN }
                )
            }
        }
    }

    private fun observeUsers() {
        viewModelScope.launch {
            userRepository.combinedUsers.observeForever() {
                uiState = uiState.copy(
                    user = userRepository.profile.value?.toUiModel(),
                )
            }
        }
    }
}