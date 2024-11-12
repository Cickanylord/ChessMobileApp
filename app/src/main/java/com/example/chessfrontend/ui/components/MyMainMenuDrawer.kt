package com.example.chessfrontend.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterStart
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.colorspace.ColorSpaces
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.chessfrontend.R
import com.example.chessfrontend.ui.model.UserUiModel
import com.example.chessfrontend.ui.theme.DrawerBackgroundColor
import com.example.chessfrontend.ui.viewmodels.MainMenuAction
import com.example.chessfrontend.ui.viewmodels.MainMenuState

@Composable
fun MyMainMenuDrawer(
    state: MainMenuState,
    onNavigationToProfile: (UserUiModel) -> Unit,
    onAction: (MainMenuAction) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth(0.7f)
            .background(DrawerBackgroundColor)
            .clip(
                RoundedCornerShape(
                    topEnd = 16.dp,
                    bottomEnd = 16.dp
                )
            ),
        //horizontalAlignment = Alignment.CenterHorizontally
    ) {
        println("color: ${MaterialTheme.colorScheme.onBackground}")
        Spacer(Modifier.windowInsetsTopHeight(WindowInsets.statusBars))
        DividerItem(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )
        ProfileBoxDrawer(
            state.user,
            onProfileClicked = onNavigationToProfile,
            onAction = onAction
        )
        DividerItem(modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
        )
        DrawerItemHeader(stringResource(R.string.friends))
        LazyColumn(
            modifier = Modifier
                .navigationBarsPadding()
        ) {
            items(state.friends){ friend ->
                FriendItem(friend, onNavigationToProfile)
            }

        }
    }
}
@Composable
private fun DrawerItemHeader(text: String) {
    Box(
        modifier = Modifier
            .padding(horizontal = 28.dp)
            .heightIn(min = 52.dp),
        contentAlignment = CenterStart
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            color = Color.White
        )
    }
}

@Composable
private fun FriendItem(
    user: UserUiModel,
    onFriendClicked: (UserUiModel) -> Unit)

{
    Row(
        modifier = Modifier
            .height(56.dp)
            .fillMaxWidth()
            .padding(horizontal = 12.dp)
            .clip(CircleShape)
            .clickable(onClick = { onFriendClicked(user) }),
        verticalAlignment = CenterVertically
    ) {

        Image(
            painter = painterResource(id = R.drawable.blank_profile_picture),
            modifier = Modifier
                .padding(start = 16.dp, top = 16.dp, bottom = 16.dp)
                .clip(CircleShape)
                .border(
                    width = 2.dp,
                    color = MaterialTheme.colorScheme.secondary.copy(alpha = 0.5f),
                    shape = CircleShape
                )
            ,
            contentDescription = "profile_picture"
        )
        Text(
            text = user.name,
            modifier = Modifier.padding(start = 12.dp),
            style = MaterialTheme.typography.bodyMedium,
            color = Color.White
        )
    }

}

@Composable
fun ProfileBoxDrawer(
    user: UserUiModel?,
    onProfileClicked: (UserUiModel) -> Unit,
    onAction: (MainMenuAction) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {

        Image(
            modifier = Modifier
                .padding(4.dp)
                .fillMaxWidth(0.45f)
                .border(
                    width = 2.dp,
                    color = MaterialTheme.colorScheme.secondary.copy(alpha = 0.5f),
                    shape = CircleShape
                )
                .clip(CircleShape)
                .clickable(
                    onClick = {
                        onAction(MainMenuAction.CloseDrawer)
                        onProfileClicked(user ?: UserUiModel())
                    }
                )
            ,
            painter = painterResource(id = R.drawable.blank_profile_picture),
            contentDescription = "profile"
        )


        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = user?.name ?: "Ethan",
            color = Color.White,
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.height(3.dp))

        Text(
            text = "ethan@gmail.com",
            color = Color.White,
            style = MaterialTheme.typography.titleMedium
        )
    }
}

@Composable
fun DividerItem(modifier: Modifier = Modifier) {
    HorizontalDivider(
        modifier = modifier,
        color = MaterialTheme.colorScheme.onSurface
    )
}



@Composable
@Preview(
    showSystemUi = true,
    showBackground = true
)
fun MyMainMenuDrawerPreview() {
    MyMainMenuDrawer(
        MainMenuState(),
        {},
        {}
    )
}