package com.example.chessfrontend.data.dataSource

import android.util.Log
import com.example.chessfrontend.data.dataSource.interfaces.MatchDataSource
import com.example.chessfrontend.data.localStorage.LocalStorage
import com.example.chessfrontend.data.model.MatchEntity
import com.example.chessfrontend.data.model.MatchRequestEntity
import com.example.chessfrontend.data.model.StepRequestEntity
import com.example.chessfrontend.data.netwrok.ChessApiService
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class MatchDataSourceImpl @Inject constructor(
    private val chessApiService: ChessApiService,
    private val localStorage: LocalStorage
): MatchDataSource {
    override suspend fun getMatches(): List<MatchEntity> {
        var matches: List<MatchEntity>

        try {
            matches = chessApiService.getMatches()
            updateMainMenuMatch(matches)
        } catch (e: Exception) {
            Log.e("MatchDataSource", "Error fetching matches: ${e.message}")
            matches = emptyList()
        }
        return matches + getOfflineMatches()
    }

    override suspend fun getMatchById(id: Long): MatchEntity? {
        try {
            return chessApiService.getMatch(id)
        } catch (e: Exception) {
            Log.e("MatchDataSource", "Error fetching match by ID: ${e.message}")
            return null
        }
    }

    override suspend fun getMatchesBetweenUsers(partnerId: Long): List<MatchEntity> {
        try {
            return chessApiService.getMatchesBetweenUsers(partnerId)
        } catch (e: Exception) {
            Log.e("MatchDataSource", "Error fetching match by ID: ${e.message}")
            return emptyList()
        }
    }

    override suspend fun getOfflineMatches(): List<MatchEntity> {
        val aiGame = MatchEntity(
            id = -2L,
            board = localStorage
                .getAiGame()
                .first()
        )
        val offlineGame = MatchEntity(
            id = -3L,
            board = localStorage
                .getOfflineGame()
                .first()
        )
        return listOf(aiGame, offlineGame)
    }

    override suspend fun step(step: StepRequestEntity): MatchEntity? {
        try {
            return chessApiService.step(step)
        } catch (e: Exception) {
            Log.e("MatchDataSource", "Error fetching match by ID: ${e.message}")
            return null
        }
    }


    private suspend fun updateMainMenuMatch(matches: List<MatchEntity>) {
        val mainMenuMatch = localStorage
            .getLasOnlineGame()
            .first()

        val mainMenuMatchEntity: MatchEntity? = matches.find { it.id == mainMenuMatch }

        if (mainMenuMatchEntity != null && mainMenuMatchEntity.isGoing) {
            return
        }

        matches.firstOrNull { it.isGoing }?.let { ongoingMatch ->
            localStorage.saveLasOnlineGame(ongoingMatch.id)
        }
    }

    override suspend fun postMatch(challenged: Long): MatchEntity? {
        try {
            return chessApiService.postMatch(
                MatchRequestEntity(
                challenged = challenged,
                board = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1")
            )
        } catch (e: Exception) {
            Log.e("MatchDataSource", "Error fetching match by ID: ${e.message}")
            return null
        }
    }


}