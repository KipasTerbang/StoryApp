package com.dicoding.storyapp.core.domain.usecase.user

import com.dicoding.storyapp.core.data.source.local.entity.UserEntity
import com.dicoding.storyapp.core.data.source.remote.network.ApiResponse
import com.dicoding.storyapp.core.data.source.remote.request.LoginRequest
import com.dicoding.storyapp.core.data.source.remote.request.RegisterRequest
import com.dicoding.storyapp.core.data.source.remote.response.LoginResponse
import com.dicoding.storyapp.core.data.source.remote.response.MessageResponse
import kotlinx.coroutines.flow.Flow

interface UserUseCase {
    fun register(registerRequest: RegisterRequest): Flow<ApiResponse<MessageResponse>>
    fun login(loginRequest: LoginRequest): Flow<ApiResponse<LoginResponse>>
    suspend fun setLogin(user: UserEntity)
    fun getLogin(): Flow<UserEntity>
    suspend fun deleteLogin()
}