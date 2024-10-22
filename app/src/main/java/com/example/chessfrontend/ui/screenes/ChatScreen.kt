package com.example.chessfrontend.ui.screenes

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.chessfrontend.ui.viewmodels.ChatUiState
import com.example.chessfrontend.ui.viewmodels.ChatViewModel
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.chessfrontend.data.model.MessageEntity
import com.example.chessfrontend.ui.model.MessageUiModel
import com.example.chessfrontend.ui.viewmodels.ChatAction
import java.time.LocalDateTime

@Composable
fun ChatScreenRoot(
    chatViewModel: ChatViewModel = hiltViewModel()
) {
   ChatScreenContent(
       chatViewModel.uiState,
       onAction = chatViewModel::handleAction
   )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreenContent(
    state: ChatUiState,
    onAction: (ChatAction) -> Unit
) {
    val localFocusManager = LocalFocusManager.current
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Chat with ") }
            )
        },

        content = { innerPadding ->
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = innerPadding,
                reverseLayout = true,
            ) {
                items(state.messages) { message ->
                    val isUserMe = message.sender != state.friend?.id
                    MessageItem(
                        message = message,
                        isUserMe = isUserMe
                    )
                }
            }
        },
        bottomBar =  {
            MessageInputField(
                onSendMessage = { messageText ->
                    localFocusManager.clearFocus()
                    onAction(ChatAction.SendMessage(messageText))
                }
            )
        }
    )
}


@Composable
fun MessageItem(
    message: MessageUiModel,
    isUserMe: Boolean
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = if (isUserMe) Arrangement.End else Arrangement.Start
    ) {
        Box(
            modifier = Modifier
                .padding(8.dp)
                .widthIn(max = 300.dp)
        ) {
            Card(
                shape = RoundedCornerShape(8.dp),
                colors = CardDefaults.cardColors(
                    containerColor = if (isUserMe) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary
                ),
                modifier = Modifier.wrapContentSize()
            ) {
                Text(
                    text = message.text,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier
                        .padding(12.dp)
                        .wrapContentSize(Alignment.Center)
                )
            }
        }
    }
}



@Composable
fun MessageInputField(
    onSendMessage: (String) -> Unit
) {
    var messageText by remember { mutableStateOf("") }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextField(
            value = messageText,
            onValueChange = { messageText = it },
            modifier = Modifier
                .weight(1f)
                .padding(end = 8.dp)
                .wrapContentHeight()
                .clip(RoundedCornerShape(25.dp)),
            placeholder = { Text("Type a message...") },

        )
        IconButton(
            onClick = {
                if (messageText.isNotBlank()) {
                    onSendMessage(messageText)
                    messageText = ""
                }
            },
            modifier = Modifier
                .size(48.dp)
                .clip(RoundedCornerShape(50))
                .background(MaterialTheme.colorScheme.primary)
        ) {
            Icon(
                Icons.AutoMirrored.Filled.Send,
                contentDescription = "Send Message",
                tint = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}



@Preview
@Composable
fun ChatScreenContentPreview() {
    ChatScreenContent(
        state = ChatUiState(
            messages = listOf(
                MessageUiModel(
                    id = 1,
                    text = "Hello",
                    sender = 1L,
                    receiver = 4L,
                    sentDate = LocalDateTime.now()
                ),
            )
        ),
        onAction = {}
    )
            //user = User(1, "me", listOf(), listOf(), listOf(), listOf(), listOf(), listOf()),
            //partner = User(2, "friend1", listOf(), listOf(), listOf(), listOf(), listOf(), listOf()),
}