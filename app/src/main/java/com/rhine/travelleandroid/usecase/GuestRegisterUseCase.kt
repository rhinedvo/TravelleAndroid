package com.rhine.travelleandroid.usecase

import com.rhine.travelleandroid.data.repository.AuthRepository
import javax.inject.Inject

class GuestRegisterUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(): Result<Boolean> {
        return authRepository.guestRegister()
    }
}
