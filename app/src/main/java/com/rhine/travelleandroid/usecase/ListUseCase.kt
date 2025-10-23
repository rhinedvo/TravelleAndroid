package com.rhine.travelleandroid.usecase

import com.rhine.travelleandroid.data.repository.ListRepository
import javax.inject.Inject

class ListUseCase @Inject constructor(
    private val repository: ListRepository
) {
    suspend operator fun invoke(): Result<Boolean> = repository.getList()
}


