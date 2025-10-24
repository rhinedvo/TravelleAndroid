package com.rhine.travelleandroid.domain.usecase.user

import com.rhine.travelleandroid.domain.repository.UserRepository
import javax.inject.Inject

class DeleteUserUseCase @Inject constructor(
    private val repository: UserRepository
) {
    suspend operator fun invoke(): Result<Boolean> {
        return repository.deleteProfile()
    }
}