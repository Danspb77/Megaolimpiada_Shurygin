package com.dev.sport.data.repository

interface AuthRepository {
    fun isLoggedIn(): Boolean
    suspend fun login(email: String, password: String): Result<Unit>
    fun logout()
}
