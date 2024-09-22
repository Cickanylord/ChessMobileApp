package com.example.chessfrontend.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.chessfrontend.ui.screenes.LoginScreenRoot
import com.example.chessfrontend.ui.screenes.RegisterScreenRoot
import com.example.chessfrontend.ui.viewmodels.LoginViewModel


// Define routes as strings
const val LOGIN_ROUTE = "login"
const val REGISTER_ROUTE = "register"
const val MAIN_MENU_ROUTE = "main_menu"

@Composable
fun LoginNavHost(
    navController: NavHostController = rememberNavController(),
    loginViewModel: LoginViewModel,
) {
    NavHost(
        navController = navController,
        startDestination = LOGIN_ROUTE
    ) {
        composable(LOGIN_ROUTE) {
            LoginScreenRoot(
                onNavigationToRegister = { navController.navigate(REGISTER_ROUTE) },
                onNavigationToForgottenPassword = { },
                loginViewModel
            )
        }
        composable(REGISTER_ROUTE) {
            RegisterScreenRoot(
                onNavigationToLogin = { navController.navigate(LOGIN_ROUTE) }
            )
        }
    }
}
