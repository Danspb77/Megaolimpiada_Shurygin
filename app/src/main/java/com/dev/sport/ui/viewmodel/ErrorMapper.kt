package com.dev.sport.ui.viewmodel

import retrofit2.HttpException
import java.io.IOException
import java.net.UnknownHostException

fun Throwable?.toUserMessage(): String {
    if (this == null) return "Unknown error"
    return when (this) {
        is HttpException -> {
            when (code()) {
                401, 403 -> "API key is missing or invalid (code ${code()})."
                404 -> "Endpoint not found (code 404)."
                else -> "Server error (code ${code()})."
            }
        }
        is UnknownHostException -> "No internet connection."
        is IOException -> "Network error. Please try again."
        else -> "Unexpected error: ${message ?: "unknown"}"
    }
}
