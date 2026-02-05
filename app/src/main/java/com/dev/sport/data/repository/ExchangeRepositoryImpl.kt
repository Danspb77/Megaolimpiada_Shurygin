package com.dev.sport.data.repository

import com.dev.sport.data.api.ApiLeagueDto
import com.dev.sport.domain.model.Exchange
import com.google.gson.reflect.TypeToken

class ExchangeRepositoryImpl(
    private val api: com.dev.sport.data.api.StockStatsApi
) : ExchangeRepository {
    override suspend fun getExchanges(): Result<List<Exchange>> =
        runCatching {
            val raw = api.getLeaguesRaw()
            val leagues = com.dev.sport.data.api.JsonParsing.parseList(
                element = raw,
                typeToken = object : TypeToken<List<ApiLeagueDto>>() {},
                arrayKeys = listOf("data", "results", "items", "leagues")
            )
            leagues.mapNotNull { it.toExchangeOrNull() }
        }
}

private fun ApiLeagueDto.toExchangeOrNull(): Exchange? {
    val exchangeId = id ?: return null
    val countryName = country?.name ?: "Unknown country"
    return Exchange(
        id = exchangeId,
        name = name ?: "Unknown league",
        countryName = countryName,
        logoUrl = logo ?: image,
        countryFlag = country?.flag ?: country?.logo ?: country?.image
    )
}