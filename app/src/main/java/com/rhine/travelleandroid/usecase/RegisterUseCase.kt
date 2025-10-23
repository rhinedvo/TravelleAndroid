package com.rhine.travelleandroid.usecase

import com.rhine.travelleandroid.data.repository.AuthRepository
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(name: String, email: String, password: String): Result<Boolean> {
        return authRepository.register(name, email, password)
    }
}
