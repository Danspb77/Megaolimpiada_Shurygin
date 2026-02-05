package com.dev.sport.data.repository

import kotlinx.coroutines.delay

class AuthRepositoryImpl : AuthRepository {
    private var loggedIn = false

    override fun isLoggedIn(): Boolean = loggedIn

    override suspend fun login(email: String, password: String): Result<Unit> {
        delay(400)
        loggedIn = true
        return Result.success(Unit)
    }

    override fun logout() {
        loggedIn = false
    }
}
