package com.rhine.travelleandroid.domain.usecase.auth

import com.kodetechnologies.guzoandroid.date.repository.AuthRepository
import toothpick.InjectConstructor

@InjectConstructor
class CheckEmailUseCase (
    private val repository: AuthRepository
) {
    suspend fun checkEmail(email: String): Result<Boolean> {
        return try {
            repository.checkEmail(email)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}