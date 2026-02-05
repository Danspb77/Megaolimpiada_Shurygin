package com.dev.sport.ui.screens.details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.dev.sport.ui.viewmodel.StockEventDetailsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StockEventDetailsScreen(
    eventId: Long,
    viewModel: StockEventDetailsViewModel
) {
    LaunchedEffect(eventId) {
        viewModel.load(eventId)
    }

    val state = viewModel.state.collectAsState().value

    Scaffold(
        topBar = { TopAppBar(title = { Text("Event details") }) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            state.errorMessage?.let {
                Text(text = it, color = MaterialTheme.colorScheme.error)
                Spacer(modifier = Modifier.height(12.dp))
            }
            val details = state.details
            if (details == null) {
                Text(text = if (state.isLoading) "Loading..." else "No data")
                return@Column
            }
            Text(text = details.startTime, style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "${details.issuerA} vs ${details.issuerB}",
                style = MaterialTheme.typography.headlineSmall
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "${details.exchangeName} • ${details.countryName}")
            details.status?.let {
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Status: $it")
            }
            details.scoreText?.let {
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Score: $it")
            }
        }
    }
}
