package com.rhine.travelleandroid.domain.usecase

import com.rhine.travelleandroid.domain.repository.ListRepository
import javax.inject.Inject

class ListUseCase @Inject constructor(
    private val repository: ListRepository
) {
    suspend operator fun invoke(): Result<Boolean> {
        return repository.getList()
    }
}