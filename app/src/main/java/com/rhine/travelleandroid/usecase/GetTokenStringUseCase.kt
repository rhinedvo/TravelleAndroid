package com.rhine.travelleandroid.usecase

import com.rhine.travelleandroid.data.local.repository.TokenRepository
import javax.inject.Inject

class GetTokenStringUseCase @Inject constructor(
    private val tokenRepository: TokenRepository
) {
    suspend operator fun invoke(): String? = tokenRepository.getTokenString()
}


