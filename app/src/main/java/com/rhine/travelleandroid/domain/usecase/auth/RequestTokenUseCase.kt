package com.rhine.travelleandroid.domain.usecase.auth

import com.kodetechnologies.guzoandroid.date.repository.AuthRepository
import toothpick.InjectConstructor

@InjectConstructor
class RequestTokenUseCase(
    private val repository: AuthRepository
) {
    suspend fun requestToken(): Result<Boolean> {
        return repository.requestToken()
    }
}