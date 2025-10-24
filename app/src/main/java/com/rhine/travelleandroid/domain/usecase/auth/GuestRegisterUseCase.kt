package com.rhine.travelleandroid.domain.usecase.auth

import com.rhine.travelleandroid.domain.repository.AuthRepository

import javax.inject.Inject

class GuestRegisterUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke() {
        authRepository.guestRegister()
    }
}