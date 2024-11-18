package com.example.chessfrontend.ui.viewmodels.gameModes

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

    override fun step(move: Pair<Int, Int>) {
        val queryBoard = uiState.board
        queryBoard
            .boardLogic
            .move(queryBoard.getPiece(uiState.clickedPiece!!.position), move)

        viewModelScope.launch {
            matchRepository.step(
                StepRequestEntity(
                    matchId = uiState.matchId,
                    board = queryBoard.toString()
                )
            )
        }
    }

    private fun observeMatches() {
        viewModelScope.launch {
            matchRepository.matches.observeForever() { matches ->
                val currentMatch = matches.find { it.id == uiState.matchId }?.toUiModel()!!

                uiState = uiState.copy(
                    boardState = currentMatch,
                    clickedPiece = null,
                    legalMoves = listOf(),
                    playMoveSound = currentMatch.board.toString() == uiState.board.toString()
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