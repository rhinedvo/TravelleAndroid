package com.rhine.travelleandroid.domain.usecase.user

import com.kodetechnologies.guzoandroid.date.model.UserDTO
import com.rhine.travelleandroid.data.repository.UserRepository
import toothpick.InjectConstructor

@InjectConstructor
class EditUserUseCase(
    private val repository: UserRepository
) {
    suspend fun execute(profile: UserDTO): Result<Boolean> {
        return repository.editProfile(profile)
    }
}