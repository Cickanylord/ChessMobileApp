package com.example.chessfrontend.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.chessfrontend.R
import com.example.chessfrontend.ui.components.DisplayWinLoseRatio
import com.example.chessfrontend.ui.components.InteractionBar
import com.example.chessfrontend.ui.components.MatchCard
import com.example.chessfrontend.ui.components.ProfileHeader
import com.example.chessfrontend.ui.model.MatchUiModel
import com.example.chessfrontend.ui.model.UserUiModel
import com.example.chessfrontend.ui.viewmodels.MainMenuAction
import com.example.chessfrontend.ui.viewmodels.ProfileAction
import com.example.chessfrontend.ui.viewmodels.ProfileUiState
import com.example.chessfrontend.ui.viewmodels.ProfileViewModel

@Composable
fun MyRectangularProfilePicture(
    modifier: Modifier = Modifier,
    user: UserUiModel,
    onClick: () -> Unit
) {
    val painter: Painter = rememberAsyncImagePainter(
        model = user.profilePicture,
        placeholder = painterResource(id = R.drawable.blank_profile_picture)
    )

    Image(
        modifier = Modifier
            .then(modifier)
            .padding(4.dp)
            .padding(start = 12.dp)
            .clickable { onClick() }
            .aspectRatio(1f)
            .border(
                width = 2.dp,
                color = MaterialTheme.colorScheme.secondary.copy(alpha = 0.5f),
                shape = RoundedCornerShape(8.dp)
            )
            .clip(RoundedCornerShape(8.dp)),
        painter = painter,
        contentScale = ContentScale.Crop,
        contentDescription = ""
    )

}