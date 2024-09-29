package com.example.chessfrontend.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import com.example.chessfrontend.MainActivity
import com.example.chessfrontend.ui.components.FormHeading
import com.example.chessfrontend.ui.navigation.MainNavHost
import com.example.chessfrontend.ui.screenes.ChatScreenContent
import com.example.chessfrontend.ui.screenes.ChatScreenRoot
import com.example.chessfrontend.ui.theme.ChessFrontEndTheme
import com.example.chessfrontend.ui.viewmodels.ChatViewModel
import com.example.chessfrontend.ui.viewmodels.FriendListViewModel
import com.example.chessfrontend.ui.viewmodels.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainMenuActivity : ComponentActivity() {
    private val friendListViewModel: FriendListViewModel by viewModels()
    private val profileViewModel: ProfileViewModel by viewModels()
    private val chatViewModel: ChatViewModel by viewModels()

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ChessFrontEndTheme {
                Scaffold(
                    content = { padding ->
                        //println("Padding: $padding")
                        padding
                        ChatScreenRoot(chatViewModel)
//                           MainNavHost (
//                                paddingValues = padding,
//                                profileViewModel = profileViewModel,
//                                friendListViewModel = friendListViewModel
//                           )
                    }
                )
                val intent: Intent = Intent(this, MainActivity::class.java)

                if (profileViewModel.uiState.logout) {
                    this.startActivity(intent)
                }
            }
        }
    }
}

