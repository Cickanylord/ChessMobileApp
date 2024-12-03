package com.example.chessfrontend.ui.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chessfrontend.data.MatchRepository
import com.example.chessfrontend.data.UserRepository
import com.example.chessfrontend.ui.model.MatchUiModel
import com.example.chessfrontend.ui.model.UserUiModel
import com.example.chessfrontend.ui.model.WinLoseRatioModel
import com.example.chessfrontend.ui.model.countWinLoseRatio
import com.example.chessfrontend.ui.model.toUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val matchRepository: MatchRepository,
    savedStateHandle: SavedStateHandle
): ViewModel() {
    var uiState by mutableStateOf(ProfileUiState())
        private set

    init {
        val id = savedStateHandle.get<Long>("userId")
        uiState = uiState.copy(id = id ?: -1L)
        loadData()
        observeMatches()
        observeUsers()
    }

    fun handleAction(action: ProfileAction) {
        when (action) {
            is ProfileAction.LoadData -> loadData()
            is ProfileAction.PostMatch -> postMatch()
        }
    }

    private fun postMatch() {
        viewModelScope.launch {
            matchRepository.postMatch(uiState.user.id)
        }
    }

    private fun loadData() {
        viewModelScope.launch {
            matchRepository.getMatches()
            //userRepository.getProfile()
        }
    }


    private fun observeMatches() {
        viewModelScope.launch {
            matchRepository.matches.observeForever() { matches ->
                uiState = uiState.copy(
                    matches = matches
                        .map { it.toUiModel() }
                        .filter { match ->
                            listOf(match.challenger, match.challenged).run {
                                contains(uiState.user.id) && contains(uiState.myProfile.id)
                            }
                        }
                )
                uiState = uiState.copy(
                    winLoseRatio = countWinLoseRatio(
                        uiState.matches,
                        uiState.myProfile.id
                    )
                )
            }
        }
    }

    private fun observeUsers() {
        viewModelScope.launch {
            userRepository.combinedUsers.observeForever() { users ->
                uiState = uiState.copy(
                    user = users.find { it.id == uiState.id }?.toUiModel() ?: UserUiModel(),
                    isMyProfile = uiState.id == userRepository.profile.value?.id,
                    myProfile = userRepository.profile.value?.toUiModel() ?: UserUiModel(),
                    users = users.map { it.toUiModel() },
                )
            }
        }
    }
}

sealed interface ProfileAction {
    data object LoadData : ProfileAction
    data object PostMatch: ProfileAction
}

data class ProfileUiState(
    val id: Long = -1L,
    val user: UserUiModel = UserUiModel(),
    val logout: Boolean = false,
    val matches: List<MatchUiModel> = emptyList(),
    val myProfile: UserUiModel = UserUiModel(),
    val isMyProfile: Boolean = false,
    val users: List<UserUiModel> = emptyList(),
    val winLoseRatio: WinLoseRatioModel = WinLoseRatioModel(-1, -1)
)
