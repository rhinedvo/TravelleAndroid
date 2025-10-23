package com.rhine.travelleandroid.domain.usecase.user

import com.rhine.travelleandroid.data.repository.UserRepository
import toothpick.InjectConstructor

@InjectConstructor
class ChangeEmailUseCase(
    private val repository: UserRepository,
) {
    suspend fun changeEmail(newEmail: String): Result<Boolean> {
        return repository.changeEmail(newEmail)
    }
}