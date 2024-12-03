package com.example.chessfrontend.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter

@Composable
fun MyIconButtonWithSmallText(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit,
    icon: Painter,
){
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        IconButton(
            onClick = { onClick() }
        ) {
            Image(
                painter = icon,
                contentDescription = text,
                colorFilter = (if (isSystemInDarkTheme()) ColorFilter.tint(Color.White) else null)
            )
        }
        Text(
            text = text,
            style = MaterialTheme.typography.bodySmall
        )
    }
}