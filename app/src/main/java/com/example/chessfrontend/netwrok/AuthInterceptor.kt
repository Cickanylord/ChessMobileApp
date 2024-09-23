package com.example.chessfrontend.netwrok

import android.content.Context
import android.util.Log
import com.example.chessfrontend.data.DataStore.getToken
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response


class AuthInterceptor(@ApplicationContext private val context: Context) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val requestBuilder = chain.request().newBuilder()

        if (original.url.encodedPath.contains("/auth")
        ) {

            return  chain.proceed(original)
        }

        val token = runBlocking { getToken(context).first().accessToken }

        Log.d("AuthInterceptor", "Token: $token")

        requestBuilder.addHeader("Authorization", "Bearer $token")
        return chain.proceed(requestBuilder.build())
    }
}