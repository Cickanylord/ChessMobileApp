package com.example.chessfrontend.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.chessfrontend.ui.screenes.FriendListScreenRoot
import com.example.chessfrontend.ui.screenes.MainMenuContent
import com.example.chessfrontend.ui.screenes.ProfileScreenRoot
import com.example.chessfrontend.ui.viewmodels.FriendListViewModel
import com.example.chessfrontend.ui.viewmodels.ProfileViewModel


private const val MainMenuRoute = "main_menu"
private const val onlineGameRoute = "online_game"
private const val offlineGameRoute = "offline_game"
private const val aiGameRoute = "ai_game"
private const val profileRoute = "profile"
private const val friendListRoute = "friend_list"

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
                onNavigationToProfile = { navController.navigate(profileRoute) }
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
                friendliestViewModel = friendListViewModel
            )
        }
    }
}