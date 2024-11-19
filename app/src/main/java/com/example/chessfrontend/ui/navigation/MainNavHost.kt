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
import com.example.chessfrontend.ui.components.UploadPictureRoot
import com.example.chessfrontend.ui.screenes.ChatScreenRoot
import com.example.chessfrontend.ui.screenes.FriendListScreenRoot
import com.example.chessfrontend.ui.screenes.MainMenuRoot
import com.example.chessfrontend.ui.screenes.MatchesScreenRoot
import com.example.chessfrontend.ui.screenes.OnlineBoardScreenRoot
import com.example.chessfrontend.ui.screenes.ProfileScreenRoot
import com.example.chessfrontend.ui.screenes.UserListScreenRoot
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
private const val matchesRoute = "matchesLost"
private const val lisOfUsersRoute = "list_of_users"
private const val profilePictureUploadRoute = "profile_picture_upload"

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
                    navController.navigate("$onlineGameRoute/${match.first}/${match.second}")
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
                onNavigationToChat = { user ->
                    navController.navigate("$chatRoute/${user.id}")
                },
                onNavigationToOnlineGame = { match ->
                    navController.navigate("$onlineGameRoute/${match.first}/${match.second}")
                },
                onNavigationToProfilePictureUpload = {navController.navigate(profilePictureUploadRoute)},
                profileViewModel = hiltViewModel(),

            )
        }



        composable(
            route = "$chatRoute/{userId}",
            arguments = listOf(navArgument("userId") { type = NavType.LongType })
        ) {
            ChatScreenRoot(
                chatViewModel = hiltViewModel(),
                onNavigationToProfile = { user ->
                    navController.navigate("$profileRoute/${user.id}")
                }
            )
        }

        composable(
            route = "$onlineGameRoute/{matchId}/{userId}",
            arguments = listOf(
                navArgument("matchId") { type = NavType.LongType },
                navArgument("userId") { type = NavType.LongType }
            ),
        ) { backStackEntry ->
            val matchJson = backStackEntry.arguments?.getString("matchId") ?: ""
            OnlineBoardScreenRoot(
                boardViewModel = hiltViewModel<OnlineBoardViewModelImpl>(),
                chatViewModel = hiltViewModel(),
                onNavigationToProfile = { user ->
                    navController.navigate("$profileRoute/${user.id}")
                }
            )

        }
        composable(profilePictureUploadRoute) {
            UploadPictureRoot(
                uploadImageViewModel = hiltViewModel()
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