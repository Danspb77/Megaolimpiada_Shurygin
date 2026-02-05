package com.dev.sport.data.repository

import android.os.Build
import androidx.annotation.RequiresApi
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import java.time.LocalDateTime

class StockEventRepositoryImpl(
    private val api: com.dev.sport.data.api.StockStatsApi
) : StockEventRepository {

    override suspend fun getUpcomingStockEvents(
        from: LocalDateTime,
        to: LocalDateTime
    ): Result<List<com.dev.sport.domain.model.StockEvent>> = runCatching {
        val params = buildParams(
            from = com.dev.sport.util.DateTimeFormatters.formatApi(from),
            to = com.dev.sport.util.DateTimeFormatters.formatApi(to),
            upcoming = true,
            ended = false
        )
        val raw = api.getGamesRaw(params)
        val games = com.dev.sport.data.api.JsonParsing.parseList(
            element = raw,
            typeToken = object : TypeToken<List<JsonObject>>() {},
            arrayKeys = listOf("data", "results", "items", "games", "matches")
        )
        games.mapNotNull { com.dev.sport.data.api.GameJsonMapper.toStockEvent(it) }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun searchStockEvents(
        leagueId: Long,
        window: com.dev.sport.domain.model.TimeWindow,
        showArchive: Boolean
    ): Result<List<com.dev.sport.domain.model.StockEvent>> = runCatching {
        val now = LocalDateTime.now()
        val to = now.plusDays(window.days)
        val params = buildParams(
            from = com.dev.sport.util.DateTimeFormatters.formatApi(now),
            to = com.dev.sport.util.DateTimeFormatters.formatApi(to),
            leagueId = leagueId,
            upcoming = !showArchive,
            ended = showArchive
        )
        val raw = api.getGamesRaw(params)
        val games = com.dev.sport.data.api.JsonParsing.parseList(
            element = raw,
            typeToken = object : TypeToken<List<JsonObject>>() {},
            arrayKeys = listOf("data", "results", "items", "games", "matches")
        )
        games.mapNotNull { com.dev.sport.data.api.GameJsonMapper.toStockEvent(it) }
    }

    override suspend fun getStockEventDetails(id: Long): Result<com.dev.sport.domain.model.StockEventDetails> =
        runCatching {
            val raw = api.getGameRaw(id)
            val obj = when {
                raw.isJsonObject -> {
                    val root = raw.asJsonObject
                    val data = root.get("data")?.takeIf { it.isJsonObject }?.asJsonObject
                    val game = data?.get("game")?.takeIf { it.isJsonObject }?.asJsonObject
                    game ?: data ?: root
                }

                else -> throw IllegalStateException("Details response is not an object")
            }
            com.dev.sport.data.api.GameJsonMapper.toStockEventDetails(obj)
                ?: throw IllegalStateException("Empty details response")
        }

    private fun buildParams(
        from: String,
        to: String,
        leagueId: Long? = null,
        upcoming: Boolean? = null,
        ended: Boolean? = null
    ): Map<String, String> {
        val map = mutableMapOf<String, String>()
        map["From"] = from
        map["To"] = to
        leagueId?.let {
            map["LeagueId"] = it.toString()
        }
        upcoming?.let {
            map["Upcoming"] = it.toString()
        }
        ended?.let {
            map["Ended"] = it.toString()
        }
        return map
    }
}