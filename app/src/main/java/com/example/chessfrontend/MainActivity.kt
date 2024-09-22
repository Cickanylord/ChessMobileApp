package com.example.chessfrontend

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import com.example.chessfrontend.data.DataStore.storeCredentials
import com.example.chessfrontend.ui.MainMenuActivity
import com.example.chessfrontend.ui.LoginNavHost
import com.example.chessfrontend.ui.theme.ChessFrontEndTheme
import com.example.chessfrontend.ui.viewmodels.LoginViewModel
import com.example.chessfrontend.ui.viewmodels.RegisterViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val loginViewModel: LoginViewModel by viewModels()
    private val registerViewModel: RegisterViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            ChessFrontEndTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                ) {

                    //navController.navigate(MAIN_MENU_ROUTE)
                    val state = loginViewModel.uiState
                    val intent: Intent = Intent(this, MainMenuActivity::class.java)
                    if (state.isLoggedIn) {
                        LaunchedEffect(Unit) {
                            storeCredentials(this@MainActivity, state.userName, state.password, state.token)
                                this@MainActivity.startActivity(intent)
                        }

                    }

                    LoginNavHost(
                        //isLoggedIn = false,
                        loginViewModel = loginViewModel,
                    )
                }
            }
        }
    }
}

