package com.example.chessfrontend.ui.components

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource

@Composable
fun MyFloatingActionButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    imageVector: ImageVector = Icons.Filled.Add,
    @StringRes contentDescription: Int,
) {
    FloatingActionButton(
        modifier = modifier,
        onClick = { onClick() },
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.onPrimary
    ) {
        Icon(

            imageVector = imageVector,
            contentDescription = stringResource(contentDescription)
        )
    }
}