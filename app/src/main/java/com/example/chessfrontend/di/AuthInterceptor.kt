package com.example.chessfrontend.di

import android.util.Log
import com.example.chessfrontend.data.localStorage.UserPreferencesRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response


class AuthInterceptor(
    private val userPreferencesRepository: UserPreferencesRepository
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val requestBuilder = chain.request().newBuilder()

        if (original.url.encodedPath.contains("/auth")
            || original.url.encodedPath.contains("/ai")
            ) {
            return  chain.proceed(original)
        }

        val token = runBlocking { userPreferencesRepository.getToken().first().accessToken }

        Log.d("AuthInterceptor", "Token: $token")

        requestBuilder.addHeader("Authorization", "Bearer $token")
        return chain.proceed(requestBuilder.build())
    }
}