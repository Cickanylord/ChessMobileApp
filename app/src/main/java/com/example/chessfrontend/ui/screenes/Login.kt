package com.example.chessfrontend.ui.screenes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.chessfrontend.R
import com.example.chessfrontend.ui.components.CustomButton
import com.example.chessfrontend.ui.components.FormHeading
import com.example.chessfrontend.ui.components.MyClickableText
import com.example.chessfrontend.ui.components.MyToast
import com.example.chessfrontend.ui.components.PasswordField
import com.example.chessfrontend.ui.components.UserNameField
import com.example.chessfrontend.ui.viewmodels.LoginAction
import com.example.chessfrontend.ui.viewmodels.LoginUiState
import com.example.chessfrontend.ui.viewmodels.LoginViewModel


@Composable
fun LoginScreenRoot(
    onNavigationToRegister: () -> Unit,
    onNavigationToForgottenPassword: () -> Unit,
    loginViewModel: LoginViewModel = hiltViewModel()
) {
    LoginScreenContent(
        onNavigationToRegister = onNavigationToRegister,
        onNavigationToForgottenPassword = onNavigationToForgottenPassword,
        state = loginViewModel.uiState,
        onAction = loginViewModel::handleAction
    )
}

@Composable
fun LoginScreenContent(
    onNavigationToRegister: () -> Unit,
    onNavigationToForgottenPassword: () -> Unit,
    state: LoginUiState,
    onAction: (LoginAction) -> Unit,
) {
    val context = LocalContext.current


    if (state.showToast) {
        MyToast(state.toastMessage, context)
        onAction(LoginAction.ToggleToast)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
                .fillMaxHeight(),
            verticalArrangement = Arrangement.Top,
        ) {
            FormHeading(text = stringResource(R.string.welcome_text))

            UserNameField(
                userName = state.userName,
                onValueChange = { onAction(LoginAction.SetUserName(it)) }
            )

            Spacer(modifier = Modifier.height(6.dp))

            PasswordField(
                password = state.password,
                onValueChange = { onAction(LoginAction.SetPassword(it)) }
            )

            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                CustomButton(
                    text = stringResource(R.string.login),
                    onClick = { onAction(LoginAction.Login) },
                )

                CustomButton(
                    text = stringResource(R.string.register),
                    onClick = {
                        onNavigationToRegister()
                    },
                )

                Spacer(modifier = Modifier.height(3.dp))

                Row {
                    MyClickableText(stringResource(R.string.forgot_password), {})
                }

            }
        }
    }
}

@Preview
@Composable
fun LoginScreenContentPreview() {
    LoginScreenContent(
        onNavigationToRegister = {},
        onNavigationToForgottenPassword = {},
        state = LoginUiState(
            userName = "test",
            password = "test",
            toastMessage = "test",
            isLoggedIn = false,
            showToast = false
        ),
        onAction = {}
    )
}