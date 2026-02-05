package com.dev.sport.data.repository

import com.dev.sport.domain.model.Exchange

interface ExchangeRepository {
    suspend fun getExchanges(): Result<List<Exchange>>
}
