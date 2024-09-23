package com.example.chessfrontend.ui.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


@Composable
fun CustomButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isEnabled: Boolean = true,
    width: Dp = 200.dp,
    height: Dp = 75.dp,
    shape: Shape = RoundedCornerShape(16.dp),
    content: @Composable () -> Unit = {
        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge.copy(
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        )
    }
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .width(width)
            .height(height)
            .padding(6.dp),
        enabled = isEnabled,
        shape = shape,
        contentPadding = PaddingValues(16.dp)
    ) {
        content()
    }
}

