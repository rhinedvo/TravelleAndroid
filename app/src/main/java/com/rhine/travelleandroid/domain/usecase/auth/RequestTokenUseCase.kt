package com.rhine.travelleandroid.domain.usecase.auth


import com.rhine.travelleandroid.domain.repository.AuthRepository
import javax.inject.Inject

class RequestTokenUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(): Result<Boolean> {
        return repository.requestToken()
    }
}