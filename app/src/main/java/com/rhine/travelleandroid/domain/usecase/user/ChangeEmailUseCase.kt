package com.rhine.travelleandroid.domain.usecase.user

import com.rhine.travelleandroid.domain.repository.UserRepository
import javax.inject.Inject

class ChangeEmailUseCase @Inject constructor(
    private val repository: UserRepository
) {
    suspend operator fun invoke(newEmail: String): Result<Boolean> {
        return repository.changeEmail(newEmail)
    }
}