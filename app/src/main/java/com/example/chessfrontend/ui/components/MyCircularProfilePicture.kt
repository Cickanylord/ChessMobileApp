package com.example.chessfrontend.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import coil.compose.rememberAsyncImagePainter
import com.example.chessfrontend.R
import com.example.chessfrontend.ui.model.UserUiModel


@Composable
fun MyCircularProfilePicture(
    modifier: Modifier = Modifier,
    model: Any?,
    onClick: () -> Unit = {},
    isClickable: Boolean = true
) {
    SubcomposeAsyncImage(
        model = model, // Pass the original model here
        loading = {
            CircularProgressIndicator()
        },
        error = {
            Image(
                painter = painterResource(id = R.drawable.blank_profile_picture),
                contentDescription = "Error loading image"
            )
        },
        modifier = Modifier
            .then(modifier)
            .clip(CircleShape)
            .border(
                width = 2.dp,
                color = MaterialTheme.colorScheme.secondary.copy(alpha = 0.5f),
                shape = CircleShape
            )
            .aspectRatio(1f)
            .clickable(
                enabled = isClickable,
                onClick = onClick
            ),
        contentScale = ContentScale.Crop,
        contentDescription = "profile_picture"
    )
}