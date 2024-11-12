package com.example.chessfrontend.ui.navigation

import androidx.activity.compose.BackHandler

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.chessfrontend.ui.components.BoardScreenRoot
import com.example.chessfrontend.ui.screenes.ChatScreenRoot
import com.example.chessfrontend.ui.screenes.FriendListScreenRoot
import com.example.chessfrontend.ui.screenes.MainMenuRoot
import com.example.chessfrontend.ui.screenes.MatchesScreenRoot
import com.example.chessfrontend.ui.screenes.ProfileScreenRoot
import com.example.chessfrontend.ui.screenes.UserListScreenRoot
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
private const val matchesRoute = "matchesLost"
private const val lisOfUsersRoute = "list_of_users"

@Composable
fun MainNavHost(
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        navController = navController,
        startDestination = MainMenuRoute,

    ) {
        composable(MainMenuRoute) {
            MainMenuRoot (
                mainMenuViewModel = hiltViewModel(),
                onNavigationToProfile = { user ->
                    navController.navigate("$profileRoute/${user.id}")
                },
                onNavigationToOnlineGame = { match ->
                    navController.navigate("$onlineGameRoute/${match.id}")
                },
                onNavigationToOfflineGame = { navController.navigate(offlineGameRoute) },
                onNavigationToAiGame = { navController.navigate(aiGameRoute) }
            )
        }

        composable(
            route = "$profileRoute/{userId}",
            arguments = listOf(navArgument("userId") { type = NavType.LongType })
        ) {
            ProfileScreenRoot(
                onNavigationToFriendList = {navController.navigate(friendListRoute)},
                onNavigationToGames = {},
                onNavigationToChat = { user ->
                    val userJson = Gson().toJson(user)
                    navController.navigate("$chatRoute/$userJson")
                },
                onNavigationToOnlineGame = { match ->
                    navController.navigate("$onlineGameRoute/${match.id}")
                },
                profileViewModel = hiltViewModel()
            )
        }

        composable(friendListRoute) {
            FriendListScreenRoot(
                friendliestViewModel = hiltViewModel(),
                onNavigationToChat = { user ->
                    val userJson = Gson().toJson(user)
                    navController.navigate("$chatRoute/$userJson")
                },
                onNavigationToUsers = {navController.navigate(lisOfUsersRoute)},
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
            arguments = listOf(navArgument("matchId") { type = NavType.LongType }),
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
            BackHandler {
                navController.navigate(MainMenuRoute)
            }
        }

        composable(aiGameRoute) {
            BoardScreenRoot(
                viewModel = hiltViewModel<AiBoardViewModelImpl>()
            )
        }

        composable(lisOfUsersRoute) {
            UserListScreenRoot(
                userListViewModel = hiltViewModel()
            )
        }
    }
}