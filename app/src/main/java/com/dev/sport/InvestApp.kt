package com.dev.sport

import android.app.Application
import com.dev.sport.di.AppContainer

class InvestApp : Application() {
    lateinit var container: AppContainer
        private set

    override fun onCreate() {
        super.onCreate()
        container = AppContainer(this)
    }
}
