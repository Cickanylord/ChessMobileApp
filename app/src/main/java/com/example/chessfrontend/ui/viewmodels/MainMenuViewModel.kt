package com.example.chessfrontend.ui.viewmodels

import androidx.compose.material3.DrawerValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chessfrontend.data.MatchRepository
import com.example.chessfrontend.data.UserRepository
import com.example.chessfrontend.data.localStorage.UserPreferencesRepository
import com.example.chessfrontend.data.netwrok.ChessApiService
import com.example.chessfrontend.ui.model.MatchUiModel
import com.example.chessfrontend.ui.model.UserUiModel
import com.example.chessfrontend.ui.model.toUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainMenuViewModel @Inject constructor(
    private val chessApiService: ChessApiService,
    private val userPreferencesRepository: UserPreferencesRepository,
    private val savedStateHandle: SavedStateHandle,
    private val matchRepository: MatchRepository,
    private val userRepository: UserRepository

): ViewModel() {
    var uiState: MainMenuState by mutableStateOf(MainMenuState())
        private set

    init {
        loadData()
        observeMatches()
        observeUsers()
    }

    fun handleAction(action: MainMenuAction) {
        when (action) {
            is MainMenuAction.OpenDrawer -> openDrawer()
            is MainMenuAction.CloseDrawer -> closeDrawer()
            is MainMenuAction.LoadData -> loadData()
        }
    }

    private fun loadData() {
        viewModelScope.launch {
            matchRepository.getMatches()
            userRepository.getProfile()
            userRepository.getFriends()
        }
    }

    private fun observeUsers() {
        viewModelScope.launch {
            userRepository.combinedUsers.observeForever() {
                uiState = uiState.copy(
                    user = userRepository.profile.value?.toUiModel(),
                    friends = userRepository.friends.value?.map { it.toUiModel() } ?: emptyList()
                )
            }
        }
    }

    private fun observeMatches() {
        viewModelScope.launch {
            val lastOnlineGame = userPreferencesRepository
                .getLasOnlineGame()
                .first()

            matchRepository.matches.observeForever() { matches ->
                uiState = uiState.copy(
                    matches = matches.map { it.toUiModel() },
                    continueMatchBoard = matches.find { it.id == lastOnlineGame }?.toUiModel() ?: MatchUiModel(),
                    aiBoard = matches.find { it.id == -2L }?.toUiModel() ?: MatchUiModel(),
                    offlineBoard = matches.find { it.id == -3L }?.toUiModel() ?: MatchUiModel(),
                )
            }
        }
    }

    private fun openDrawer() {
        uiState = uiState.copy(
            drawerState = DrawerValue.Open
        )
    }

    private fun closeDrawer() {
        uiState = uiState.copy(
            drawerState = DrawerValue.Closed
        )
    }
}

sealed interface MainMenuAction {
    data object OpenDrawer : MainMenuAction
    data object CloseDrawer : MainMenuAction
    data object LoadData : MainMenuAction
}

data class MainMenuState(
    val user: UserUiModel? = null,
    val aiBoard: MatchUiModel = MatchUiModel(),
    val offlineBoard: MatchUiModel = MatchUiModel(),
    val onlineBoard: MatchUiModel = MatchUiModel(),
    val continueMatchBoard: MatchUiModel = MatchUiModel(),
    val drawerState: DrawerValue = DrawerValue.Closed,
    val friends: List<UserUiModel> = emptyList(),
    val matches: List<MatchUiModel> = emptyList()
)