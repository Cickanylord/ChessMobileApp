package com.example.chessfrontend.ui.screenes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.chessfrontend.ui.components.CustomButton
import com.example.chessfrontend.ui.components.FormHeading

@Composable
fun MainMenuContent(
    onNavigationToProfile: () -> Unit = {}
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
            onClick = { /* Handle play game button click */ }
        )
        CustomButton(
            text = "Offline Game",
            onClick = { /* Handle offline game button click */ }
        )
        CustomButton(
            text = "AI Game",
            onClick = { /* Handle settings button click */ }
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

