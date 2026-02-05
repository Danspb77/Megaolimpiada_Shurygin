package com.dev.sport.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dev.sport.data.repository.StockEventRepository
import com.dev.sport.domain.model.StockEvent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDateTime

class StockFeedViewModel(
    private val repository: StockEventRepository
) : ViewModel() {

    private val _state = MutableStateFlow(StockFeedUiState())
    val state: StateFlow<StockFeedUiState> = _state

    init {
        loadUpcoming()
    }

    fun loadUpcoming() {
        _state.update { it.copy(isLoading = true, errorMessage = null) }
        viewModelScope.launch {
            val now = LocalDateTime.now()
            val to = now.plusDays(2)
            val result = repository.getUpcomingStockEvents(now, to)
            _state.update {
                if (result.isSuccess) {
                    it.copy(isLoading = false, events = result.getOrDefault(emptyList()))
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

data class StockFeedUiState(
    val events: List<StockEvent> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)
