package com.dev.sport.ui.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.dev.sport.di.AppViewModelFactory
import com.dev.sport.ui.screens.auth.AuthGatewayScreen
import com.dev.sport.ui.screens.details.StockEventDetailsScreen
import com.dev.sport.ui.screens.screener.ScreenerScreen
import com.dev.sport.ui.screens.stockfeed.StockFeedScreen
import com.dev.sport.ui.viewmodel.AuthGatewayViewModel
import com.dev.sport.ui.viewmodel.StockEventDetailsViewModel
import com.dev.sport.ui.viewmodel.StockFeedViewModel
import com.dev.sport.ui.viewmodel.ScreenerViewModel

@Composable
fun AppNav(factory: AppViewModelFactory) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "auth") {
        composable("auth") {
            val viewModel: AuthGatewayViewModel = viewModel(factory = factory)
            AuthGatewayScreen(
                viewModel = viewModel,
                onLoggedIn = {
                    navController.navigate("stock_feed") {
                        popUpTo("auth") { inclusive = true }
                    }
                }
            )
        }
        composable("stock_feed") {
            val viewModel: StockFeedViewModel = viewModel(factory = factory)
            StockFeedScreen(
                viewModel = viewModel,
                onOpenScreener = { navController.navigate("screener") },
                onOpenDetails = { id -> navController.navigate("details/$id") }
            )
        }
        composable("screener") {
            val viewModel: ScreenerViewModel = viewModel(factory = factory)
            ScreenerScreen(
                viewModel = viewModel,
                onOpenDetails = { id -> navController.navigate("details/$id") },
                onBack = { navController.popBackStack() }
            )
        }
        composable("details/{id}") { backStackEntry ->
            val eventId = backStackEntry.arguments?.getString("id")?.toLongOrNull()
                ?: return@composable
            val viewModel: StockEventDetailsViewModel = viewModel(factory = factory)
            StockEventDetailsScreen(eventId = eventId, viewModel = viewModel)
        }
    }
}
