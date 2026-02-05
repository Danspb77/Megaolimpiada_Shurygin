package com.dev.sport.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dev.sport.ui.viewmodel.AuthGatewayViewModel
import com.dev.sport.ui.viewmodel.ScreenerViewModel
import com.dev.sport.ui.viewmodel.StockEventDetailsViewModel
import com.dev.sport.ui.viewmodel.StockFeedViewModel

class AppViewModelFactory(
    private val container: AppContainer
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(AuthGatewayViewModel::class.java) ->
                AuthGatewayViewModel(container.authRepository) as T
            modelClass.isAssignableFrom(StockFeedViewModel::class.java) ->
                StockFeedViewModel(container.stockEventRepository) as T
            modelClass.isAssignableFrom(ScreenerViewModel::class.java) ->
                ScreenerViewModel(container.exchangeRepository, container.stockEventRepository) as T
            modelClass.isAssignableFrom(StockEventDetailsViewModel::class.java) ->
                StockEventDetailsViewModel(container.stockEventRepository) as T
            else -> throw IllegalArgumentException("Unknown ViewModel ${modelClass.simpleName}")
        }
    }
}
