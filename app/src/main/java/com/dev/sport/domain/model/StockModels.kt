package com.dev.sport.domain.model

data class StockEvent(
    val id: Long,
    val startTime: String,
    val issuerA: String,
    val issuerB: String,
    val exchangeName: String,
    val countryName: String,
    val issuerALogo: String?,
    val issuerBLogo: String?,
    val exchangeLogo: String?,
    val countryFlag: String?
)

data class StockEventDetails(
    val id: Long,
    val startTime: String,
    val issuerA: String,
    val issuerB: String,
    val exchangeName: String,
    val countryName: String,
    val issuerALogo: String?,
    val issuerBLogo: String?,
    val exchangeLogo: String?,
    val countryFlag: String?,
    val status: String?,
    val scoreText: String?
)

data class Exchange(
    val id: Long,
    val name: String,
    val countryName: String,
    val logoUrl: String?,
    val countryFlag: String?
)

enum class TimeWindow(val label: String, val days: Long) {
    DAY("Day", 1),
    WEEK("Week", 7),
    MONTH("Month", 30)
}
