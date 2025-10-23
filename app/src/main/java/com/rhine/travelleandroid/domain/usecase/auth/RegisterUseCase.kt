package com.rhine.travelleandroid.domain.usecase.auth

import com.kodetechnologies.guzoandroid.date.repository.AuthRepository
import toothpick.InjectConstructor

@InjectConstructor
class RegisterUseCase(
    private val repository: AuthRepository
) {
    suspend fun register(name: String, email: String, password: String): Result<Boolean> {
        return repository.register(name, email, password)
    }
}