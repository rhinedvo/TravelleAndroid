package com.rhine.travelleandroid.domain.usecase.user

import com.rhine.travelleandroid.data.remote.dto.UserDTO
import com.rhine.travelleandroid.domain.repository.UserRepository
import javax.inject.Inject

class EditUserUseCase @Inject constructor(
    private val repository: UserRepository
) {
    suspend operator fun invoke(profile: UserDTO): Result<Boolean> {
        return repository.editProfile(profile)
    }
}