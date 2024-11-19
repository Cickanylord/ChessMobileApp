package com.example.chessfrontend.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.chessfrontend.ui.viewmodels.ProfileUiState

@Composable
fun ProfileHeader(
state: ProfileUiState,
    onCLick: () -> Unit,
) {
    Row (
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .padding(8.dp)
        ,
        horizontalArrangement = Arrangement.Start

    ) {

        MyCircularProfilePicture(
            model = state.user.profilePicture,
            onClick = onCLick,
            isClickable = state.isMyProfile
        )

        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                state.user.name,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                fontSize = MaterialTheme.typography.titleLarge.fontSize * 1.4
            )
            Text(
                "${state.user.name}@gmial.com",
                style = MaterialTheme.typography.titleMedium,
                fontSize = MaterialTheme.typography.titleMedium.fontSize
            )
        }
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun MyProfileHeaderPreview() {
    ProfileHeader(state = ProfileUiState(), {})
}