package com.rhine.travelleandroid.domain.usecase

import com.rhine.travelleandroid.data.repository.AuthRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(email: String, password: String): Result<Boolean> {
        return authRepository.login(email, password)
    }
}
