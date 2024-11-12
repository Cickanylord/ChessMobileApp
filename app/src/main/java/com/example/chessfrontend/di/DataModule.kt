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
import com.example.chessfrontend.data.localStorage.UserPreferencesRepository
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
    fun provideUserPreferencesRepository(@ApplicationContext context: Context): UserPreferencesRepository {
        return UserPreferencesRepositoryImpl(context)
    }

    @Provides
    @Singleton
    fun provideMatchDataSource(
        chessApiService: ChessApiService,
        userPreferencesRepository: UserPreferencesRepository
    ): MatchDataSource {
        return MatchDataSourceImpl(
            chessApiService = chessApiService,
            userPreferencesRepository = userPreferencesRepository
        )
    }

    @Provides
    @Singleton
    fun provideMatchRepository(
        dataSource: MatchDataSource,
        userPreferencesRepository: UserPreferencesRepository
    ): MatchRepository {
        return MatchRepositoryImpl(
            dataSource = dataSource,
            userPreferencesRepository = userPreferencesRepository
        )
    }

    @Provides
    @Singleton
    fun provideUserDataSource(
        userPreferencesRepository: UserPreferencesRepository,
        chessApiService: ChessApiService
    ): UserDataSource {
        return UserDataSourceImpl(
            userPreferencesRepository = userPreferencesRepository,
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