package com.example.chessfrontend.ui.screenes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
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
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextField
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import com.example.chessfrontend.data.model.MessageEntity


private val ChatBubbleShape = RoundedCornerShape(4.dp, 20.dp, 20.dp, 20.dp)

@Composable
fun ChatScreenRoot(
    chatViewModel: ChatViewModel = hiltViewModel()
) {
   ChatScreenContent(chatViewModel.uiState)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreenContent(
    state: ChatUiState
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Chat with ") }
            )
        },

        content = { innerPadding ->
            LazyColumn(
                contentPadding = innerPadding,
                reverseLayout = true
            ) {
                items(state.messages) { message ->
                    val isUserMe = message.sender == 2L
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
                    // Handle sending the message
                    //state.onSendMessage(messageText)
                }
            )
        }
    )
}

@Composable
fun MessageItem(
    message: MessageEntity,
    isUserMe: Boolean
) {
    val contentAlignment = if (isUserMe) Alignment.CenterEnd else Alignment.CenterStart
    val containerColor = if (isUserMe) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = contentAlignment,
    ) {
        Card(
            modifier = Modifier
                .padding(8.dp)
                .wrapContentSize()
                .heightIn(
                    min = 50.dp
                )
                .widthIn(
                    max = 300.dp
                )
                .align(contentAlignment),
            colors = CardDefaults.cardColors(
                containerColor = containerColor
            )

        ) {
            Row(
                modifier = Modifier
                    .padding(8.dp)
                    .wrapContentSize(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically

            ) {
                Text(
                    text = message.text,
                    style = MaterialTheme.typography.bodyLarge,
                    //textAlign = if (isUserMe) TextAlign.End else TextAlign.Start
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
            .padding(8.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextField(
            value = messageText,
            onValueChange = { messageText = it },
            modifier = Modifier.weight(1F),
            placeholder = { Text("Type a message...") }
        )
        IconButton(
            onClick = {
                if (messageText.isNotBlank()) {
                    onSendMessage(messageText)
                    messageText = "" // Clear input after sending
                }
            }
        ) {
            Icon(Icons.Default.Send, contentDescription = "Send Message")
        }
    }
}


@Preview
@Composable
fun ChatScreenContentPreview() {
    ChatScreenContent(
        state = ChatUiState(
            messages = listOf(
                MessageEntity(
                    id = 1,
                    text = "Hello",
                    sender = 1L,
                    receiver = 4L,
                    sentDate = "asd"
                ),
            )
        )
    )
            //user = User(1, "me", listOf(), listOf(), listOf(), listOf(), listOf(), listOf()),
            //partner = User(2, "friend1", listOf(), listOf(), listOf(), listOf(), listOf(), listOf()),
}