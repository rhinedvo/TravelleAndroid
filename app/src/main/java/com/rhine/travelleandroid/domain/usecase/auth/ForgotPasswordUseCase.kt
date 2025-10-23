package com.rhine.travelleandroid.domain.usecase.auth

import com.kodetechnologies.guzoandroid.date.repository.AuthRepository
import toothpick.InjectConstructor

@InjectConstructor
class ForgotPasswordUseCase(
    private val repository: AuthRepository
) {
    suspend fun forgotPassword(email: String): Result<Boolean> {
        return repository.forgotPassword(email)
    }
}