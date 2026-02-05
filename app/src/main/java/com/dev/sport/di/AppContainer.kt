package com.dev.sport.di

import android.content.Context
import com.dev.sport.data.api.ApiKeyInterceptor
//import com.dev.sport.BuildConfig
//import com.dev.sport.data.api.ApiKeyInterceptor
import com.dev.sport.data.api.StockStatsApi
import com.dev.sport.data.repository.AuthRepository
import com.dev.sport.data.repository.AuthRepositoryImpl
import com.dev.sport.data.repository.ExchangeRepository
import com.dev.sport.data.repository.ExchangeRepositoryImpl
import com.dev.sport.data.repository.StockEventRepository
import com.dev.sport.data.repository.StockEventRepositoryImpl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AppContainer(context: Context) {
        private val apiKeyInterceptor = ApiKeyInterceptor("0qvr1usy3gmo5uys")

    private val okHttpClient: OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(apiKeyInterceptor)
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .build()

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://api.sstats.net/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()

    private val api: StockStatsApi = retrofit.create(StockStatsApi::class.java)

    val stockEventRepository: StockEventRepository = StockEventRepositoryImpl(api)
    val exchangeRepository: ExchangeRepository = ExchangeRepositoryImpl(api)
    val authRepository: AuthRepository = AuthRepositoryImpl()
}
