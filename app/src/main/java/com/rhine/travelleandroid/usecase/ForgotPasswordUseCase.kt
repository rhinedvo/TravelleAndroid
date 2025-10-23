package com.rhine.travelleandroid.usecase

import com.rhine.travelleandroid.data.repository.AuthRepository
import javax.inject.Inject

class ForgotPasswordUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(email: String): Result<Boolean> {
        return authRepository.forgotPassword(email)
    }
}
