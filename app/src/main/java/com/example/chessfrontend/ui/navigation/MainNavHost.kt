package com.example.chessfrontend.ui.navigation

import androidx.activity.viewModels
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.chessfrontend.ui.components.BoardScreenRoot
import com.example.chessfrontend.ui.model.toEntity
import com.example.chessfrontend.ui.screenes.ChatScreenRoot
import com.example.chessfrontend.ui.screenes.FriendListScreenRoot
import com.example.chessfrontend.ui.screenes.MainMenuContent
import com.example.chessfrontend.ui.screenes.MatchesScreenRoot
import com.example.chessfrontend.ui.screenes.ProfileScreenRoot
import com.example.chessfrontend.ui.viewmodels.FriendListViewModel
import com.example.chessfrontend.ui.viewmodels.ProfileViewModel
import com.example.chessfrontend.ui.viewmodels.gameModes.AiBoardViewModelImpl
import com.example.chessfrontend.ui.viewmodels.gameModes.OfflineBoardViewModelImpl
import com.example.chessfrontend.ui.viewmodels.gameModes.OnlineBoardViewModelImpl
import com.google.gson.Gson

private const val MainMenuRoute = "main_menu"
private const val onlineGameRoute = "online_game"
private const val offlineGameRoute = "offline_game"
private const val aiGameRoute = "ai_game"
private const val profileRoute = "profile"
private const val friendListRoute = "friend_list"
private const val chatRoute = "chat"
private const val matchesRoute = "matches"

@Composable
fun MainNavHost(
    paddingValues: PaddingValues,
    profileViewModel: ProfileViewModel,
    friendListViewModel: FriendListViewModel,
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        navController = navController,
        startDestination = MainMenuRoute,
        modifier = Modifier.padding(paddingValues) // Apply padding to the NavHost
    ) {
        composable(MainMenuRoute) {
            MainMenuContent(
                onNavigationToProfile = { navController.navigate(profileRoute) },
                onNavigationToOnlineGame = { navController.navigate(onlineGameRoute) },
                onNavigationToOfflineGame = { navController.navigate(offlineGameRoute) },
                onNavigationToAiGame = { navController.navigate(aiGameRoute) }
            )
        }
        composable(profileRoute) {
            ProfileScreenRoot(
                onNavigationToFriendList = {navController.navigate(friendListRoute)},
                onNavigationToGames = {},
                profileViewModel = profileViewModel
            )
        }
        composable(friendListRoute) {
            FriendListScreenRoot(
                friendliestViewModel = friendListViewModel,
                onNavigationToChat = { user ->
                    val userJson = Gson().toJson(user)
                    navController.navigate("$chatRoute/$userJson")
                },
                onNavigationToMatches = { user ->
                    val userJson = Gson().toJson(user)
                    navController.navigate("$matchesRoute/$userJson")
                }
            )
        }

        composable(
            route = "$matchesRoute/{userJson}",
            arguments = listOf(navArgument("userJson") { type = NavType.StringType })
            ) {
            MatchesScreenRoot(
                matchesViewModel = hiltViewModel(),
                onNavigationToMatch = { match ->
                    navController.navigate("$onlineGameRoute/${match.id}")
                }
            )
        }

        composable(
            route = "$chatRoute/{userJson}",
            arguments = listOf(navArgument("userJson") { type = NavType.StringType })
        ) { backStackEntry ->
            val userJson = backStackEntry.arguments?.getString("userJson") ?: ""
            println("ChatId: $userJson")
            ChatScreenRoot(
                chatViewModel = hiltViewModel(),
            )
        }

        composable(
            route = "$onlineGameRoute/{matchId}",
            arguments = listOf(navArgument("matchId") { type = NavType.LongType })
        ) { backStackEntry ->
            val matchJson = backStackEntry.arguments?.getString("matchId") ?: ""
            BoardScreenRoot(
                viewModel = hiltViewModel<OnlineBoardViewModelImpl>()
            )
        }

        composable(offlineGameRoute) {
            BoardScreenRoot(
                viewModel = hiltViewModel<OfflineBoardViewModelImpl>()
            )
        }

        composable(aiGameRoute) {
            BoardScreenRoot(
                viewModel = hiltViewModel<AiBoardViewModelImpl>()
            )
        }
    }
}