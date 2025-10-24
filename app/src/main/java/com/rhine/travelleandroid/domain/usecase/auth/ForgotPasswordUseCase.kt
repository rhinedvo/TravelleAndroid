package com.rhine.travelleandroid.domain.usecase.auth


import com.rhine.travelleandroid.domain.repository.AuthRepository
import javax.inject.Inject

class ForgotPasswordUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(email: String): Result<Boolean> {
        return repository.forgotPassword(email)
    }
}