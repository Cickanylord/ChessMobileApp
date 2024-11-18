package com.example.chessfrontend.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource

@Composable
fun DrawPiece(
    imageID: Int,
    rotation: Float = 0f
) {
    Image(
        modifier = Modifier
            .fillMaxSize()
            .graphicsLayer {
                rotationZ = rotation
            },
        painter = painterResource(id = imageID),
        contentDescription = "chess piece"
    )
}