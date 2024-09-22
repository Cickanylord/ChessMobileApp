package com.example.chessfrontend.netwrok

import android.content.Context
import com.example.chessfrontend.data.DataStore
import com.example.chessfrontend.data.DataStore.getToken
import com.example.chessfrontend.data.model.User
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.http.GET
import javax.inject.Inject
import javax.inject.Qualifier
import javax.inject.Singleton

private const val BASE_URL: String = "http://192.168.0.89:8080/api/"

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

     @Provides
     @Singleton
     fun provideOkHttpClient(authInterceptor: AuthInterceptor): OkHttpClient {
         val logging = HttpLoggingInterceptor().apply {
             level = HttpLoggingInterceptor.Level.BODY
         }
         return OkHttpClient.Builder()
             .addInterceptor(authInterceptor)
             .addInterceptor(logging)
             .build()
     }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .build()
    }


     @Provides
     @Singleton
     fun provideChessApiService(retrofit: Retrofit): ChessApiService {
         return retrofit.create(ChessApiService::class.java)
     }

    @Provides
    @Singleton
    fun provideAuthInterceptor(@ApplicationContext context: Context): AuthInterceptor {
        return AuthInterceptor(context)
    }
}
