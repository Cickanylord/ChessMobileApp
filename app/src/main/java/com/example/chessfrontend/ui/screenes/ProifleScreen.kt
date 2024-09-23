package com.example.chessfrontend.ui.screenes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.chessfrontend.R
import com.example.chessfrontend.ui.components.CustomButton
import com.example.chessfrontend.ui.components.FormHeading
import com.example.chessfrontend.ui.viewmodels.ProfileUiState
import com.example.chessfrontend.ui.viewmodels.ProfileViewModel

@Composable
fun ProfileScreenRoot(
    onNavigationToFriendList: () -> Unit,
    onNavigationToGames: () -> Unit,
    profileViewModel: ProfileViewModel = hiltViewModel()
) {
    ProfileScreen(
        onNavigationToFriendList = onNavigationToFriendList,
        onNavigationToGames = onNavigationToGames,
        state = profileViewModel.uiState
    )

}

@Composable
fun ProfileScreen(
    onNavigationToFriendList: () -> Unit,
    onNavigationToGames: () -> Unit,
    state: ProfileUiState
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            FormHeading(text = stringResource(R.string.profile))

            Text(text = state.userName)

            Spacer(modifier = Modifier.height(6.dp))

            Text(text = state.email)

            CustomButton(
                text = "Games",
                onClick = {onNavigationToFriendList()}
            )

            CustomButton(
                text = "Friend list",
                onClick = {onNavigationToFriendList()}
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview(){
    ProfileScreen(
        onNavigationToFriendList = {},
        onNavigationToGames = {},
        state = ProfileUiState(
            userName = "John Doe",
            email = "jhon@gmail.com"
        )
    )
}