package com.example.chessfrontend.ui.components

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource

@Composable
fun DrawPiece(imageID: Int) {
    Image(
        painter = painterResource(id = imageID),
        contentDescription = "chess piece"
    )
}