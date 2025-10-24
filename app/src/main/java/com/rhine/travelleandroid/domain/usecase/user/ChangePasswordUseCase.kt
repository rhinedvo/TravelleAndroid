package com.rhine.travelleandroid.domain.usecase.user

import com.rhine.travelleandroid.domain.repository.UserRepository
import javax.inject.Inject

class ChangePasswordUseCase @Inject constructor(
    private val repository: UserRepository
) {
    suspend operator fun invoke(newPassword: String, currentPassword: String): Result<Boolean> {
        return repository.changePassword(newPassword, currentPassword)
    }
}