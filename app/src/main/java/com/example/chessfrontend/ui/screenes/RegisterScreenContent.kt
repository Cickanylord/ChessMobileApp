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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.chessfrontend.R
import com.example.chessfrontend.ui.components.CustomButton
import com.example.chessfrontend.ui.components.EmailField
import com.example.chessfrontend.ui.components.FormHeading
import com.example.chessfrontend.ui.components.MyClickableText
import com.example.chessfrontend.ui.components.MyToast
import com.example.chessfrontend.ui.components.PasswordField
import com.example.chessfrontend.ui.components.UserNameField
import com.example.chessfrontend.ui.viewmodels.RegisterAction
import com.example.chessfrontend.ui.viewmodels.RegisterUiState
import com.example.chessfrontend.ui.viewmodels.RegisterViewModel

@Composable
fun RegisterScreenRoot(
    onNavigationToLogin: () -> Unit = {},
    registerViewModel: RegisterViewModel = hiltViewModel(),
) {
    RegisterScreenContent(
        onNavigationToLogin = onNavigationToLogin,
        state = registerViewModel.uiState,
        onAction = registerViewModel::handleAction
    )
}

@Composable
fun RegisterScreenContent(
    onNavigationToLogin: () -> Unit,
    state: RegisterUiState,
    onAction: (RegisterAction) -> Unit
) {
    val context = LocalContext.current

    if (state.isRegistered) {
       LaunchedEffect(Unit) {
           onNavigationToLogin()
       }
    }
    if (state.showToast) {
        MyToast(state.toastMessage, context)
        onAction(RegisterAction.ToggleToast)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
                .fillMaxHeight(),
            verticalArrangement = Arrangement.Top,
        ) {
            FormHeading(text = "Pleas, register")

            UserNameField(
                userName = state.userName,
                onValueChange = { onAction(RegisterAction.SetUserName(it))}
            )

            Spacer(modifier = Modifier.height(12.dp))

            EmailField(
                email = state.email,
                onValueChange = { onAction(RegisterAction.SetEmail(it))}
            )

            Spacer(modifier = Modifier.height(12.dp))

            PasswordField(
                password = state.password,
                onValueChange = { onAction(RegisterAction.SetPassword(it)) }
            )

            Spacer(modifier = Modifier.height(12.dp))

            PasswordField(
                password = state.password2,
                onValueChange = { onAction(RegisterAction.SetPassword2(it))},
                text = "Your password again"
            )



            Column( modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CustomButton(
                    text = stringResource(R.string.register),
                    onClick = {
                        onAction(RegisterAction.Register)
                    },
                )

                Row{
                    MyClickableText(
                        text = "Login if you already have an account",
                        onClick = { onNavigationToLogin() }
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun RegisterScreenContentPreview() {
    RegisterScreenContent(
        onNavigationToLogin = {},
        state = RegisterUiState(
        userName = "userName",
        email = "email",
        password = "password",
        password2 = "password2"
    ), onAction = {})
}