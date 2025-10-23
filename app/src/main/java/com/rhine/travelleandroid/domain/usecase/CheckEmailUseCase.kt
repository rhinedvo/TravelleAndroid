package com.rhine.travelleandroid.domain.usecase

import com.rhine.travelleandroid.data.repository.AuthRepository
import javax.inject.Inject

class CheckEmailUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(email: String): Result<Boolean> {
        return authRepository.checkEmail(email)
    }
}
