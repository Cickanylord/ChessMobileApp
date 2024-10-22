package com.example.chessfrontend.ui.viewmodels.gameModes

import ai_engine.board.BoardData
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.example.chessfrontend.data.localStorage.UserPreferencesRepository
import com.example.chessfrontend.data.netwrok.ChessApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import javax.inject.Inject

@HiltViewModel
class AiBoardViewModelImpl @Inject constructor(
    private val chessApiService: ChessApiService,
    private val userPreferencesRepository: UserPreferencesRepository
): BoardViewModelImpl() {

    override fun step(move: Pair<Int, Int>) {
        super.step(move)
        viewModelScope.launch {
            try {
                Log.d("AiBoardViewModelImpl", userPreferencesRepository.getToken().first().accessToken)
                val response = chessApiService.getAi(FenDTO(uiState.board.toString()))
                Log.d("AiBoardViewModelImpl", response.toString())
                uiState = uiState.copy(
                    boardState = mutableStateOf(BoardData(response.fen))
                )

            } catch (e: Exception) {
                Log.d("AiBoardViewModelImpl", e.message.toString())
            }
        }
    }

    override fun loadBoard() {
        viewModelScope.launch {
            val game = userPreferencesRepository.getAiGame().first()
            uiState = uiState.copy(
                boardState = mutableStateOf(BoardData(game))
            )
        }
    }

    override fun saveGame() {
        viewModelScope.launch {
            userPreferencesRepository.saveAiGame(uiState.board.toString())
        }
    }

}
@Serializable
data class FenDTO(val fen: String)