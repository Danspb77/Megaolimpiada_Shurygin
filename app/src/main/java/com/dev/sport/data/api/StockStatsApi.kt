package com.dev.sport.data.api

import com.google.gson.JsonElement
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryMap

interface StockStatsApi {
    @GET("leagues")
    suspend fun getLeaguesRaw(): JsonElement

    @GET("games/list")
    suspend fun getGamesRaw(@QueryMap params: Map<String, String>): JsonElement

    @GET("games/{id}")
    suspend fun getGameRaw(@Path("id") id: Long): JsonElement
}
