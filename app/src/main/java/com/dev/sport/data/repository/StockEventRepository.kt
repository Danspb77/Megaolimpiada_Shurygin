package com.dev.sport.data.repository

import com.dev.sport.domain.model.StockEvent
import com.dev.sport.domain.model.StockEventDetails
import com.dev.sport.domain.model.TimeWindow
import java.time.LocalDateTime

interface StockEventRepository {
    suspend fun getUpcomingStockEvents(from: LocalDateTime, to: LocalDateTime): Result<List<StockEvent>>
    suspend fun searchStockEvents(leagueId: Long, window: TimeWindow, showArchive: Boolean): Result<List<StockEvent>>
    suspend fun getStockEventDetails(id: Long): Result<StockEventDetails>
}
