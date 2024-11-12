package com.example.chessfrontend.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.chessfrontend.ui.model.WinLoseRatioModel
import com.example.chessfrontend.ui.viewmodels.MatchesUiState

@Composable
fun DisplayWinLoseRatio(
    ratio: WinLoseRatioModel
) {
    Row(
        modifier = Modifier
            .padding(
                start = 8.dp,
                end = 8.dp
            )
            .wrapContentSize(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically

    ) {
        Text(text = "VS:")
        Card(
            modifier = Modifier
                .padding(
                    start = 16.dp,
                    end = 16.dp
                )
                .width(150.dp)
        ) {
            Row(
                modifier = Modifier
                    .background(Color.LightGray)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly

            ) {
                Text(
                    text = "${ratio.wins}W",
                    color = Color(0xFF4CAF50),
                    maxLines = 1,
                )

                Text(
                    text = "/",
                    maxLines = 1,
                )

                Text(
                    text = "${ratio.loses}L",
                    color = Color(0xFFF44336),
                    maxLines = 1,
                )
            }
        }
    }
}