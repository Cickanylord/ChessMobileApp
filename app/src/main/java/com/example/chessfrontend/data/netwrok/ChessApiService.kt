package com.example.chessfrontend.data.netwrok

import com.example.chessfrontend.data.model.MessageEntity
import com.example.chessfrontend.data.model.Token
import com.example.chessfrontend.data.model.User
import com.example.chessfrontend.data.model.UserAuth
import com.example.chessfrontend.data.model.UserPost
import com.example.chessfrontend.ui.viewmodels.gameModes.FenDTO
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ChessApiService {
    /**
     * Authenticate a user.
     * @param userAuth The user's authentication credentials.
     */
    @POST("/api/auth")
    suspend fun authenticate(@Body userAuth: UserAuth): Token

    /**
     * Register a new user.
     * @param userPost The user's registration details.
     */
    @POST("/api/user")
    suspend fun register(@Body userPost: UserPost): User

    /**
     * Get the authenticated user's profile.
     */
    @GET("/api/user/me")
    suspend fun getProfile(): User

    /**
     * Get a list of friends for the authenticated user.
     */
    @GET("/api/user/friends")
    suspend fun getFriends(): List<User>

    /**
     * get the best step for the current position
     * @param fen the board description with fen annotation
     */
    @POST("/api/chess/ai/fen")
    suspend fun getAi(@Body fen: FenDTO): FenDTO

    /**
     * Get a list of messages between the authenticated user and a specific partner.
     * @param partnerId The ID of the partner user.
     */
    @POST("/api/messages/partner/{id}")
    suspend fun getMessageBetweenUsers(@Path("id") partnerId: Long): List<MessageEntity>
}