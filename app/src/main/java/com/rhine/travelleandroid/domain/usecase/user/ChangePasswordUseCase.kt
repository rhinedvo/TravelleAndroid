package com.rhine.travelleandroid.domain.usecase.user

import com.rhine.travelleandroid.data.repository.UserRepository
import toothpick.InjectConstructor

@InjectConstructor
class ChangePasswordUseCase(
    private val repository: UserRepository
) {
    suspend fun changePassword(newPassword: String, currentPassword: String): Result<Boolean> {
        return repository.changePassword(newPassword, currentPassword)
    }
}