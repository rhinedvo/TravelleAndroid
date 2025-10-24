package com.rhine.travelleandroid.domain.usecase.auth

import com.rhine.travelleandroid.domain.repository.AuthRepository
import javax.inject.Inject

class CheckEmailUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(email: String): Result<Boolean> {
        return try {
            repository.checkEmail(email)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}