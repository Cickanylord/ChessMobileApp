package com.example.chessfrontend.di

import com.example.chessfrontend.data.localStorage.UserPreferencesRepository
import com.example.chessfrontend.data.netwrok.ChessApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
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
             .connectTimeout(130, TimeUnit.SECONDS)  // Adjust connection timeout
             .readTimeout(130, TimeUnit.SECONDS)     // Adjust read timeout
             .writeTimeout(130, TimeUnit.SECONDS)
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
    fun provideAuthInterceptor(userPreferencesRepository: UserPreferencesRepository): AuthInterceptor {
        return AuthInterceptor(userPreferencesRepository)
    }
}