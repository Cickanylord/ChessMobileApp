package com.example.chessfrontend.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.chessfrontend.data.dataSource.interfaces.MatchDataSource
import com.example.chessfrontend.data.localStorage.UserPreferencesRepository
import com.example.chessfrontend.data.model.MatchEntity
import com.example.chessfrontend.data.model.StepRequestEntity
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import javax.inject.Inject

class MatchRepositoryImpl @Inject constructor(
    private val dataSource: MatchDataSource,
    private val userPreferencesRepository: UserPreferencesRepository
) : MatchRepository  {
    private val repositoryScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    private lateinit var webSocket: WebSocket
    private val webSocketListener = MatchWebSocketListener()
    private val _matches = MutableLiveData<List<MatchEntity>>()
    override val matches: LiveData<List<MatchEntity>> = _matches

    init {
        repositoryScope.launch {
            val id = userPreferencesRepository.getUser().first()?.id ?: -1
            buildWebSocket(id)
            repositoryScope
        }
    }

    private fun buildWebSocket(id: Long) {
        val request = Request.Builder()
            .url("ws://192.168.0.89:8080/match?${id}")
            .build()

        val client = OkHttpClient()
        webSocket = client.newWebSocket(request, webSocketListener)
        repositoryScope.cancel()
    }

    override suspend fun getMatches() {
        _matches.value = dataSource.getMatches()
    }

    override suspend fun getMatch(id: Long) {
        dataSource.getMatchById(id)?.let { replaceMatch(it) }
    }

    override suspend fun step(step: StepRequestEntity) {
        dataSource.step(step)?.let { replaceMatch(it) }
    }

    override suspend fun updateMatch(match: MatchEntity) {
        replaceMatch(match)
    }

    override suspend fun postMatch(challenged: Long) {
        dataSource.postMatch(challenged)?.let { replaceMatch(it) }
    }

    override suspend fun reOpenSocket() {
        if(!webSocket.isOpen()) {
            val id = userPreferencesRepository.getUser().first()?.id ?: -1
            buildWebSocket(id)
        }
    }

    private fun replaceMatch(match: MatchEntity) {
        val currentMatches = _matches.value ?: emptyList()

        val result = currentMatches.map {
            if (it.id == match.id) match else it
        } + if (currentMatches.none { it.id == match.id }) listOf(match) else emptyList()

        _matches.postValue(result)
    }

    private fun WebSocket.isOpen(): Boolean {
        return this.send("ping") // Send a ping message to check if open
    }

    inner class MatchWebSocketListener : WebSocketListener() {
        override fun onOpen(webSocket: WebSocket, response: Response) {
            Log.d("OnlineBoardViewModelImpl", "WebSocket opened")
        }

        override fun onMessage(webSocket: WebSocket, text: String) {
            Log.d("OnlineBoardViewModelImpl", "Received message: $text")
            val matchEntity = Gson().fromJson(text, MatchEntity::class.java)
            replaceMatch(matchEntity)
        }

        override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
            Log.d("OnlineBoardViewModelImpl", "WebSocket closing: $code / $reason")
            webSocket.close(1000, null)
        }

        override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
            Log.e("OnlineBoardViewModelImpl", "WebSocket failure: ${t.message}")
        }
    }
}