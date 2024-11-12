package com.example.chessfrontend.ui

import android.content.Intent
import android.content.res.Resources.Theme
import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme

import androidx.compose.ui.graphics.toArgb
import androidx.core.content.ContextCompat
import androidx.core.text.color
import androidx.core.view.ViewCompat
import com.example.chessfrontend.MainActivity
import com.example.chessfrontend.R
import com.example.chessfrontend.ui.navigation.MainNavHost
import com.example.chessfrontend.ui.theme.ChessFrontEndTheme
import com.example.chessfrontend.ui.viewmodels.ChatViewModel

import com.example.chessfrontend.ui.viewmodels.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainMenuActivity : ComponentActivity() {


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



