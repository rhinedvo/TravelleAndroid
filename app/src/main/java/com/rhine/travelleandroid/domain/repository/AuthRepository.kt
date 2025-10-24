package com.rhine.travelleandroid.domain.repository

interface AuthRepository {
    suspend fun requestToken(): Result<Boolean>
    suspend fun login(email: String, password: String): Result<Boolean>
    suspend fun register(name: String, email: String, password: String): Result<Boolean>
    suspend fun forgotPassword(email: String): Result<Boolean>
    suspend fun guestRegister(): Result<Boolean>
    suspend fun checkEmail(email: String): Result<Boolean>
}