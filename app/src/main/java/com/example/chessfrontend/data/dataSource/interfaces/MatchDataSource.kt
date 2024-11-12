package com.example.chessfrontend.data.dataSource.interfaces

import com.example.chessfrontend.data.model.MatchEntity
import com.example.chessfrontend.data.model.StepRequestEntity

interface MatchDataSource {
    suspend fun getMatches(): List<MatchEntity>
    suspend fun getMatchById(id: Long): MatchEntity?
    suspend fun getMatchesBetweenUsers(partnerId: Long): List<MatchEntity>
    suspend fun getOfflineMatches(): List<MatchEntity>
    suspend fun step(step: StepRequestEntity): MatchEntity?
    suspend fun postMatch(challenged: Long): MatchEntity?
}