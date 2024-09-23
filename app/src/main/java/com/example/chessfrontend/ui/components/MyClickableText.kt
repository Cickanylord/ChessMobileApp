package com.example.chessfrontend.ui.components

import androidx.compose.foundation.text.ClickableText
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
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
