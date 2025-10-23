package com.rhine.travelleandroid.domain.usecase

import com.kodetechnologies.guzoandroid.date.repository.ListRepository
import toothpick.InjectConstructor

@InjectConstructor
class ListUseCase (
    private val repository: ListRepository
) {
    suspend fun execute(): Result<Boolean> {
        return repository.getList()
    }
}
