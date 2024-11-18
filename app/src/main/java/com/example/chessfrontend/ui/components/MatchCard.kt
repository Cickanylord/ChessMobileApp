package com.example.chessfrontend.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.chessfrontend.ui.model.MatchUiModel
import com.example.chessfrontend.ui.model.UserUiModel
import com.example.chessfrontend.ui.viewmodels.MatchesAction

@Composable
fun MatchCard(
    match: MatchUiModel,
    onAction: (MatchesAction) -> Unit,
    onClick: () -> Unit = {},
    tileSize: Int = 20,
    content : @Composable () -> Unit = {}
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable(onClick = { onClick() })
        ,
        shape = RoundedCornerShape(8.dp)
    ) {
        DrawMatchRow(
            match = match,
            tileSize = tileSize,
            content = content
        )
    }
}


@Composable
@Preview(showBackground = true)
fun MatchCardPreview() {
    MatchCard(
        match = MatchUiModel(),
        onAction = {},
    )
}