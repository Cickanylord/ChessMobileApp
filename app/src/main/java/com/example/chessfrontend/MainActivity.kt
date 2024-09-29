package com.example.chessfrontend

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import com.example.chessfrontend.data.localStorage.UserPreferencesRepository
import com.example.chessfrontend.data.model.UserAuth
import com.example.chessfrontend.data.netwrok.ChessApiService
import com.example.chessfrontend.ui.navigation.LoginNavHost
import com.example.chessfrontend.ui.MainMenuActivity
import com.example.chessfrontend.ui.theme.ChessFrontEndTheme
import com.example.chessfrontend.ui.viewmodels.LoginViewModel
import com.example.chessfrontend.ui.viewmodels.RegisterViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first
import javax.inject.Inject

/**
 * Main activity for the chess application.
 * this activity handles the user authentication.
 * the user credentials are stored after the first login.
 */

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    /** view models **/
    private val loginViewModel: LoginViewModel by viewModels()
    private val registerViewModel: RegisterViewModel by viewModels()

    /** intent for the next activity **/



    /** data storage **/
    @Inject
    lateinit var userPreferencesRepository: UserPreferencesRepository

    /** api service **/
    @Inject
    lateinit var chessApiService: ChessApiService

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            ChessFrontEndTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    val intent: Intent = Intent(this, MainMenuActivity::class.java)
                    LaunchedEffect (Unit) {
                        val credentials = userPreferencesRepository
                            .getCredentials()
                            .first()

                        try {
                            val token = chessApiService.authenticate(
                                UserAuth(
                                    userName = credentials.username,
                                    password = credentials.password
                                )
                            )
                            userPreferencesRepository.refreshToken(token)
                            this@MainActivity.startActivity(intent)

                        } catch (e: Exception) {
                            Log.d("MainActivity", e.message.toString())
                        }
                    }

                    LoginNavHost(
                        loginViewModel = loginViewModel
                    )
                    val loginState = loginViewModel.uiState

                    if (loginState.isLoggedIn) {
                        LaunchedEffect(Unit) {
                            userPreferencesRepository.storeCredentials(
                                username = loginState.userName,
                                password = loginState.password,
                                token = loginState.token
                            )

                            this@MainActivity.startActivity(intent)
                        }
                    }
                }
            }
        }
    }
}

