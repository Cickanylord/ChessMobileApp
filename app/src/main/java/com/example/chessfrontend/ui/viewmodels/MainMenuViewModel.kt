package com.example.chessfrontend.ui.viewmodels

import androidx.compose.material3.DrawerValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chessfrontend.data.MatchRepository
import com.example.chessfrontend.data.UserRepository
import com.example.chessfrontend.data.localStorage.LocalStorage
import com.example.chessfrontend.ui.model.MatchUiModel
import com.example.chessfrontend.ui.model.UserUiModel
import com.example.chessfrontend.ui.model.toUiModel
import com.example.chessfrontend.util.findPartnerId
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainMenuViewModel @Inject constructor(
    private val localStorage: LocalStorage,
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
            is MainMenuAction.ToggleDropDownMenu -> toggleDropDownMenu()
            is MainMenuAction.LogOut -> logOut()
            is MainMenuAction.AddNewGame -> addNewGame()
            is MainMenuAction.RemoveNewGame -> removeNewGame()
        }
    }

    private fun removeNewGame() {
        uiState = uiState.copy(
            newMatch = MatchUiModel()
        )
    }

    private fun addNewGame() {
        viewModelScope.launch {
            if (uiState.user?.friendList?.isNotEmpty() == true) {
                matchRepository.postMatch(uiState.user!!.friendList.random())
                val newMatch = uiState.matches.maxByOrNull { it.id }

                uiState = uiState.copy(
                    newMatch = newMatch!!
                )

            }
        }
    }


    private fun logOut() {
        viewModelScope.launch {
            userRepository.logOut()
        }
    }

    private fun toggleDropDownMenu() {
        uiState = uiState.copy(
            dropDownMenuOpen = !uiState.dropDownMenuOpen
        )
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
            val lastOnlineGame = localStorage
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
            if (uiState.continueMatchBoard.isGoing.not()) {
                matchRepository.getMatches()
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
    data object ToggleDropDownMenu : MainMenuAction
    data object LogOut : MainMenuAction
    data object AddNewGame : MainMenuAction
    data object RemoveNewGame : MainMenuAction
}

data class MainMenuState(
    val user: UserUiModel? = null,
    val aiBoard: MatchUiModel = MatchUiModel(),
    val offlineBoard: MatchUiModel = MatchUiModel(),
    val onlineBoard: MatchUiModel = MatchUiModel(),
    val continueMatchBoard: MatchUiModel = MatchUiModel(),
    val drawerState: DrawerValue = DrawerValue.Closed,
    val friends: List<UserUiModel> = emptyList(),
    val matches: List<MatchUiModel> = emptyList(),
    val dropDownMenuOpen: Boolean = false,
    val newMatch: MatchUiModel = MatchUiModel()
)