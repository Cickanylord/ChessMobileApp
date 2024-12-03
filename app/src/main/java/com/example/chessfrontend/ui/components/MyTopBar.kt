package com.example.chessfrontend.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.chessfrontend.ui.model.UserUiModel

@Composable
fun MyTopBar(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    user: UserUiModel,
    text: String = "",
    content: @Composable () -> Unit = {}
) {
    Column {
        Spacer(Modifier.windowInsetsPadding(WindowInsets.statusBars))
        Row(
            modifier = modifier
                .fillMaxWidth()
                .height(50.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row {
                MyRectangularProfilePicture(
                    user = user,
                    onClick = onClick,
                )

                Text(
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .padding(start = 8.dp),
                    fontSize = MaterialTheme.typography.titleLarge.fontSize,
                    fontWeight = MaterialTheme.typography.titleLarge.fontWeight,
                    text = text
                )
            }
            content()
        }
    }
}