package com.example.chessfrontend.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import com.example.chessfrontend.ui.model.MatchUiModel
import com.example.chessfrontend.ui.model.UserUiModel

@Composable
fun DrawMatchRow(
    match: MatchUiModel = MatchUiModel(),
    tileSize: Int = 20,
    content : @Composable () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,

        ) {
        Box(
            modifier = Modifier
                .weight(1f)
        ) {

            DrawSmallBoard(
                tileSize = tileSize,
                match = match
            )


        }
        Box(
            modifier = Modifier
                .weight(1f)
                .height(tileSize * 8.dp)
        ) {
            content()
        }
    }
}