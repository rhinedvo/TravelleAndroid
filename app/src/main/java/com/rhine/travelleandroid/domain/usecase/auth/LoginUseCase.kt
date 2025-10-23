package com.rhine.travelleandroid.domain.usecase.auth

import com.kodetechnologies.guzoandroid.date.repository.AuthRepository
import toothpick.InjectConstructor

@InjectConstructor
class LoginUseCase(
    private val repository: AuthRepository
) {
    suspend fun login(email: String, password: String): Result<Boolean> {
        return repository.login(email, password)
    }
}