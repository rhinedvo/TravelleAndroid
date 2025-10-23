package com.rhine.travelleandroid.domain.usecase.user

import com.rhine.travelleandroid.data.repository.UserRepository

import javax.inject.Inject

class GetUserUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke() = userRepository.getUser()
}