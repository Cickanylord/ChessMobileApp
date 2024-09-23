package com.example.chessfrontend.ui.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.foundation.Canvas
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp

@Composable
fun DrawCircle() {
    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp)
    ) {
        val canvasWidth = size.width
        val canvasHeight = size.height

        val radius = size.minDimension / 2
        drawCircle(
            color = Color(0xffb3b3b3),
            center = Offset(x = canvasWidth / 2, y = canvasHeight / 2),
            radius = radius,
            style = Stroke(10F)
        )
    }
}
