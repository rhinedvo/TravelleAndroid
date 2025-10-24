package com.rhine.travelleandroid.domain.repository

interface ListRepository {
    suspend fun getList(): Result<Boolean>
}