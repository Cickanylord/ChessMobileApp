package com.example.chessfrontend.ui.model

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.example.chessfrontend.data.model.UserEntity
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

data class UserUiModel(
    val id: Long = -1,
    val name: String = "",
    val roles: List<String> = emptyList(),
    val messagesSent: List<Long> = emptyList(),
    val messagesReceived: List<Long> = emptyList(),
    val challenger: List<Long> = emptyList(),
    val challenged: List<Long> = emptyList(),
    val friendList: List<Long> = emptyList(),
    val winedMatches: List<Long> = emptyList(),
    val lostMatches: List<Long> = emptyList(),
    val profilePicture: Bitmap? = null
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as UserUiModel

        if (id != other.id) return false
        if (name != other.name) return false
        if (roles != other.roles) return false
        if (messagesSent != other.messagesSent) return false
        if (messagesReceived != other.messagesReceived) return false
        if (challenger != other.challenger) return false
        if (challenged != other.challenged) return false
        if (friendList != other.friendList) return false
        if (winedMatches != other.winedMatches) return false
        if (lostMatches != other.lostMatches) return false
        if (profilePicture != other.profilePicture) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + roles.hashCode()
        result = 31 * result + messagesSent.hashCode()
        result = 31 * result + messagesReceived.hashCode()
        result = 31 * result + challenger.hashCode()
        result = 31 * result + challenged.hashCode()
        result = 31 * result + friendList.hashCode()
        result = 31 * result + winedMatches.hashCode()
        result = 31 * result + lostMatches.hashCode()
        result = 31 * result + profilePicture.hashCode()
        return result
    }
}

fun UserEntity.toUiModel() = UserUiModel(
    id = id,
    name = name,
    roles = roles,
    messagesSent = messagesSent,
    messagesReceived = messagesReceived,
    challenger = challenger,
    challenged = challenged,
    friendList = friendList,
    winedMatches = matchesWined,
    lostMatches = matchesLost,
    profilePicture = base64ToBitmap(profilePicture)
)

@OptIn(ExperimentalEncodingApi::class)
fun base64ToBitmap(base64String: String?): Bitmap? =
    try {
        val encodedImageData = Base64.Default.decode(base64String as CharSequence)
        BitmapFactory.decodeByteArray(encodedImageData, 0, encodedImageData.size)
    } catch (e: Exception) {
        null
    }

