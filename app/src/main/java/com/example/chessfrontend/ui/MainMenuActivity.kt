package com.example.chessfrontend.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.ExperimentalMaterial3Api

import androidx.core.view.ViewCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.chessfrontend.MainActivity
import com.example.chessfrontend.data.netwrok.ChessApiService
import com.example.chessfrontend.ui.components.UploadPictureRoot
import com.example.chessfrontend.ui.components.UploadPictureScreen
import com.example.chessfrontend.ui.navigation.MainNavHost
import com.example.chessfrontend.ui.theme.ChessFrontEndTheme

import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MainMenuActivity : ComponentActivity() {
    @Inject
    lateinit var chessApiService: ChessApiService


    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {

        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        ViewCompat.setOnApplyWindowInsetsListener(window.decorView) { _, insets -> insets }

        setContent {
            ChessFrontEndTheme {
                MainNavHost()
                val intent: Intent = Intent(this, MainActivity::class.java)
            }
        }
    }
}



