package com.example.chessfrontend.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import com.example.chessfrontend.ui.components.FormHeading
import com.example.chessfrontend.ui.theme.ChessFrontEndTheme
import com.example.chessfrontend.ui.viewmodels.FriendListViewModel
import com.example.chessfrontend.ui.viewmodels.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainMenuActivity : ComponentActivity() {
    val profileViewModel: ProfileViewModel by viewModels()
    val friendliestViewModel: FriendListViewModel by viewModels()

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ChessFrontEndTheme { // Use your app's theme
                Scaffold(
                    topBar = {
                        CenterAlignedTopAppBar(
                            title = { FormHeading("Welcome to Chess App!") }
                        )
                    },
                    content = { padding ->
                        MainNavHost (
                            paddingValues = padding,
                            profileViewModel = profileViewModel,
                            friendListViewModel = friendliestViewModel
                        )
                    }
                )
            }
        }
    }


}

