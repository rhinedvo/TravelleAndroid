package com.rhine.travelleandroid.domain.usecase.auth

import com.rhine.travelleandroid.domain.repository.AuthRepository
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(name: String, email: String, password: String): Result<Boolean> {
        return repository.register(name, email, password)
    }
}