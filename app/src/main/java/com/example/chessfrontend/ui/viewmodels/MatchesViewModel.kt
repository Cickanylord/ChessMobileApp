package com.example.chessfrontend.ui.viewmodels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chessfrontend.data.localStorage.LocalStorage
import com.example.chessfrontend.data.netwrok.ChessApiService
import com.example.chessfrontend.ui.model.MatchUiModel
import com.example.chessfrontend.ui.model.UserUiModel
import com.example.chessfrontend.ui.model.WinLoseRatioModel
import com.example.chessfrontend.ui.model.countWinLoseRatio
import com.example.chessfrontend.ui.model.toUiModel
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MatchesViewModel @Inject constructor(
    private val chessApiService: ChessApiService,
    private val localStorage: LocalStorage,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    var uiState: MatchesUiState by mutableStateOf(MatchesUiState())
        private set

    init {
        loadMatches()
    }

    fun handleAction(action: MatchesAction) {
        when (action) {
            is MatchesAction.LoadMatches -> loadMatches()
        }
    }

    private fun loadMatches() {
        viewModelScope.launch {
            val friendJson = savedStateHandle.get<String>("userJson")
            uiState = uiState.copy(
                partner = Gson().fromJson(friendJson, UserUiModel::class.java)
            )




            try {
                val matches = chessApiService.getMatchesBetweenUsers(uiState.partner!!.id)
                uiState = uiState.copy(
                    matches = matches.map { it.toUiModel() },
                    user = localStorage
                        .getUser()
                        .first()
                        !!.toUiModel()
                )

                uiState = uiState.copy(
                    winLoseRatio = countWinLoseRatio(
                        uiState.matches,
                        uiState.user!!.id
                    )
                )

            } catch (e: Exception) {
                Log.e("MatchesViewModel", "Error loading matchesLost: ${e.message}")
            }
        }
    }


}

data class MatchesUiState(
    val partner: UserUiModel? = null,
    val user: UserUiModel? = null,
    val matches: List<MatchUiModel> = listOf(),
    val winLoseRatio: WinLoseRatioModel = WinLoseRatioModel(-1, -1)
)



sealed interface MatchesAction {
    object LoadMatches : MatchesAction
}
