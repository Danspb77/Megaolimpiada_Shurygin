package com.dev.sport

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.dev.sport.di.AppViewModelFactory
import com.dev.sport.ui.navigation.AppNav
import com.dev.sport.ui.navigation.AppNav
import com.dev.sport.ui.theme.SportTheme

class PortfolioActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val container = (application as InvestApp).container
        val factory = AppViewModelFactory(container)
        setContent {
            SportTheme {
                AppNav(factory)
            }
        }
    }
}
