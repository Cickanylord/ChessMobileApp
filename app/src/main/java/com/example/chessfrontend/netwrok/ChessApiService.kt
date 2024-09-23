package com.example.chessfrontend.netwrok

import com.example.chessfrontend.data.model.Token
import com.example.chessfrontend.data.model.User
import com.example.chessfrontend.data.model.UserAuth
import com.example.chessfrontend.data.model.UserPost
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
}