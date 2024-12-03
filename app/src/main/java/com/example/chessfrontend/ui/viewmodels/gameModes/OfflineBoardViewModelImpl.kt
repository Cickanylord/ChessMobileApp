package com.example.chessfrontend.ui.viewmodels.gameModes

import ai_engine.board.BoardData
import androidx.lifecycle.viewModelScope
import com.example.chessfrontend.data.localStorage.LocalStorage
import com.example.chessfrontend.ui.model.MatchUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OfflineBoardViewModelImpl @Inject constructor(
    private val localStorage: LocalStorage
): BoardViewModelImpl() {

    init {
        loadBoard()
    }

    override fun loadBoard() {
        viewModelScope.launch {
            val game = localStorage.getOfflineGame().first().let {
                it.ifEmpty { DEFAULT_GAME }
            }

            uiState = uiState.copy(
                boardState = MatchUiModel(
                    board = BoardData(game)
                )
            )
        }
    }

    override fun saveGame() {
        viewModelScope.launch {
            localStorage.saveOfflineGame(uiState.board.toString())
        }
    }
}