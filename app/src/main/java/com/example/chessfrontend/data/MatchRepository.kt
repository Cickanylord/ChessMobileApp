package com.example.chessfrontend.data

import androidx.lifecycle.LiveData
import com.example.chessfrontend.data.model.MatchEntity
import com.example.chessfrontend.data.model.StepRequestEntity

interface MatchRepository {
    val matches: LiveData<List<MatchEntity>>
    suspend fun getMatches()
    suspend fun getMatch(id: Long)
    suspend fun step(step: StepRequestEntity)
    suspend fun updateMatch(match: MatchEntity)
    suspend fun postMatch(challenged: Long)
    suspend fun reOpenSocket()
}