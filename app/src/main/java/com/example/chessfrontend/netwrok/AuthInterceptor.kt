package com.example.chessfrontend.netwrok

import android.util.Log
import com.example.chessfrontend.data.DataStoreService
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response


class AuthInterceptor(
    private val dataStoreService: DataStoreService
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val requestBuilder = chain.request().newBuilder()

        if (original.url.encodedPath.contains("/auth")
            || original.url.encodedPath.contains("/ai")
            ) {
            return  chain.proceed(original)
        }

        val token = runBlocking { dataStoreService.getToken().first().accessToken }

        Log.d("AuthInterceptor", "Token: $token")

        requestBuilder.addHeader("Authorization", "Bearer $token")
        return chain.proceed(requestBuilder.build())
    }
}