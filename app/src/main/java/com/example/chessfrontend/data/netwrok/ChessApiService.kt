package com.example.chessfrontend.data.netwrok

import com.example.chessfrontend.data.model.Token
import com.example.chessfrontend.data.model.User
import com.example.chessfrontend.data.model.UserAuth
import com.example.chessfrontend.data.model.UserPost
import com.example.chessfrontend.ui.viewmodels.gameModes.FenDTO
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ChessApiService {
    @POST("/api/auth")
    suspend fun authenticate(@Body userAuth: UserAuth): Token

    @POST("/api/user")
    suspend fun register(@Body userPost: UserPost): User

    @GET("/api/user/me")
    suspend fun getProfile(): User

    @GET("/api/user/friends")
    suspend fun getFriends(): List<User>

    @POST("/api/chess/ai/fen")
    suspend fun getAi(@Body fen: FenDTO): FenDTO
}