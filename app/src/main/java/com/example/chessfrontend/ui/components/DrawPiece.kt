package com.example.chessfrontend.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource

@Composable
fun DrawPiece(imageID: Int) {
    Image(
        modifier = Modifier
            .fillMaxSize(),
        painter = painterResource(id = imageID),
        contentDescription = "chess piece"
    )
}