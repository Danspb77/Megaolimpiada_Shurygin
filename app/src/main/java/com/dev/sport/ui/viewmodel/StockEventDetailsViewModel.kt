package com.dev.sport.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dev.sport.data.repository.StockEventRepository
import com.dev.sport.domain.model.StockEventDetails
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class StockEventDetailsViewModel(
    private val repository: StockEventRepository
) : ViewModel() {

    private val _state = MutableStateFlow(StockEventDetailsUiState())
    val state: StateFlow<StockEventDetailsUiState> = _state

    fun load(eventId: Long) {
        if (_state.value.isLoading) return
        _state.update { it.copy(isLoading = true, errorMessage = null) }
        viewModelScope.launch {
            val result = repository.getStockEventDetails(eventId)
            _state.update {
                if (result.isSuccess) {
                    it.copy(isLoading = false, details = result.getOrNull())
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

data class StockEventDetailsUiState(
    val details: StockEventDetails? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)
