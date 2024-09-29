package com.example.chessfrontend.ui.screenes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.chessfrontend.ui.components.CustomButton

@Composable
fun MainMenuContent(
    onNavigationToProfile: () -> Unit = {},
    onNavigationToOnlineGame: () -> Unit = {},
    onNavigationToOfflineGame: () -> Unit = {},
    onNavigationToAiGame: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp), // Consistent padding
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CustomButton(
            text = "Online Game",
            onClick = { onNavigationToOnlineGame() }
        )
        CustomButton(
            text = "Offline Game",
            onClick = { onNavigationToOfflineGame() }
        )
        CustomButton(
            text = "AI Game",
            onClick = { onNavigationToAiGame() }
        )

        CustomButton(
            text = "Profile",
            onClick = { onNavigationToProfile() }
        )
    }
}
@Preview(showBackground = true)
@Composable
fun MainMenuContentPreview() {
    MainMenuContent()
}

