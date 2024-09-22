package com.example.chessfrontend.ui.components

import android.util.Log
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextGeometricTransform
import androidx.compose.ui.unit.sp

@Composable
fun MyClickableText (
    text: String,
    onClick: () -> Unit,
){
    ClickableText(
        text = AnnotatedString(text),
        onClick = {onClick()},
        style = TextStyle(
            fontWeight = FontWeight.Thin,
            fontSize = 13.sp
        )
    )
}
