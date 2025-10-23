package com.rhine.travelleandroid.domain.usecase.user

import com.rhine.travelleandroid.data.repository.UserRepository
import toothpick.InjectConstructor

@InjectConstructor
class DeleteUserUseCase(
    private val repository: UserRepository
) {
    suspend fun execute(): Result<Boolean> {
        return repository.deleteProfile()
    }
}