package com.dev.sport.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dev.sport.data.repository.ExchangeRepository
import com.dev.sport.data.repository.StockEventRepository
import com.dev.sport.domain.model.Exchange
import com.dev.sport.domain.model.StockEvent
import com.dev.sport.domain.model.TimeWindow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ScreenerViewModel(
    private val exchangeRepository: ExchangeRepository,
    private val stockEventRepository: StockEventRepository
) : ViewModel() {

    private val _state = MutableStateFlow(ScreenerUiState())
    val state: StateFlow<ScreenerUiState> = _state

    init {
        loadExchanges()
    }

    fun loadExchanges() {
        _state.update { it.copy(isLoading = true, errorMessage = null) }
        viewModelScope.launch {
            val result = exchangeRepository.getExchanges()
            _state.update {
                if (result.isSuccess) {
                    val exchanges = result.getOrDefault(emptyList())
                    it.copy(
                        isLoading = false,
                        exchanges = exchanges,
                        selectedExchange = it.selectedExchange ?: exchanges.firstOrNull()
                    )
                } else {
                    it.copy(
                        isLoading = false,
                        errorMessage = result.exceptionOrNull().toUserMessage()
                    )
                }
            }
        }
    }

    fun selectExchange(exchange: Exchange) {
        _state.update { it.copy(selectedExchange = exchange, errorMessage = null) }
    }

    fun selectWindow(window: TimeWindow) {
        _state.update { it.copy(selectedWindow = window, errorMessage = null) }
    }

    fun searchUpcoming() {
        val exchange = _state.value.selectedExchange ?: return
        performSearch(exchange.id, false)
    }

    fun searchArchive() {
        val exchange = _state.value.selectedExchange ?: return
        performSearch(exchange.id, true)
    }

    private fun performSearch(leagueId: Long, archive: Boolean) {
        _state.update { it.copy(isLoading = true, errorMessage = null) }
        viewModelScope.launch {
            val result = stockEventRepository.searchStockEvents(
                leagueId = leagueId,
                window = _state.value.selectedWindow,
                showArchive = archive
            )
            _state.update {
                if (result.isSuccess) {
                    it.copy(
                        isLoading = false,
                        results = result.getOrDefault(emptyList()),
                        showingArchive = archive
                    )
                } else {
                    it.copy(
                        isLoading = false,
                        errorMessage = result.exceptionOrNull().toUserMessage()
                    )
                }
            }
        }
    }
}

data class ScreenerUiState(
    val exchanges: List<Exchange> = emptyList(),
    val selectedExchange: Exchange? = null,
    val selectedWindow: TimeWindow = TimeWindow.WEEK,
    val results: List<StockEvent> = emptyList(),
    val showingArchive: Boolean = false,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)
