package com.example.chessfrontend.di

import android.content.Context
import com.example.chessfrontend.data.MatchRepository
import com.example.chessfrontend.data.MatchRepositoryImpl
import com.example.chessfrontend.data.UserRepository
import com.example.chessfrontend.data.UserRepositoryImpl
import com.example.chessfrontend.data.dataSource.interfaces.MatchDataSource
import com.example.chessfrontend.data.dataSource.MatchDataSourceImpl
import com.example.chessfrontend.data.dataSource.UserDataSourceImpl
import com.example.chessfrontend.data.dataSource.interfaces.UserDataSource
import com.example.chessfrontend.data.localStorage.LocalStorage
import com.example.chessfrontend.data.localStorage.UserPreferencesRepositoryImpl
import com.example.chessfrontend.data.netwrok.ChessApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {
    @Provides
    @Singleton
    fun provideUserPreferencesRepository(@ApplicationContext context: Context): LocalStorage {
        return UserPreferencesRepositoryImpl(context)
    }

    @Provides
    @Singleton
    fun provideMatchDataSource(
        chessApiService: ChessApiService,
        localStorage: LocalStorage
    ): MatchDataSource {
        return MatchDataSourceImpl(
            chessApiService = chessApiService,
            localStorage = localStorage
        )
    }

    @Provides
    @Singleton
    fun provideMatchRepository(
        dataSource: MatchDataSource,
        localStorage: LocalStorage
    ): MatchRepository {
        return MatchRepositoryImpl(
            dataSource = dataSource,
            localStorage = localStorage
        )
    }

    @Provides
    @Singleton
    fun provideUserDataSource(
        localStorage: LocalStorage,
        chessApiService: ChessApiService
    ): UserDataSource {
        return UserDataSourceImpl(
            localStorage = localStorage,
            chessApiService = chessApiService
        )
    }

    @Provides
    @Singleton
    fun provideUserRepository(
        dataSource: UserDataSource
    ): UserRepository {
        return UserRepositoryImpl(
            dataSource = dataSource
        )
    }
}