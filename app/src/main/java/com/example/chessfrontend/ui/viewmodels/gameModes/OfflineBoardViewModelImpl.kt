package com.example.chessfrontend.ui.viewmodels.gameModes

import ai_engine.board.BoardData
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.example.chessfrontend.data.localStorage.UserPreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OfflineBoardViewModelImpl @Inject constructor(
    private val userPreferencesRepository: UserPreferencesRepository
): BoardViewModelImpl() {

    init {
        loadBoard()
    }

    override fun loadBoard() {
        viewModelScope.launch {
            val game = userPreferencesRepository.getOfflineGame().first().let {
                it.ifEmpty { DEFAULT_GAME }
            }

            uiState = uiState.copy(
                boardState = mutableStateOf(BoardData(game))
            )
        }
    }

    override fun saveGame() {
        viewModelScope.launch {
            userPreferencesRepository.saveOfflineGame(uiState.board.toString())
        }
    }
}