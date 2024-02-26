package com.dicoding.storyapp.core.domain.usecase.user

import com.dicoding.storyapp.core.data.source.local.entity.UserEntity
import com.dicoding.storyapp.core.data.source.remote.network.ApiResponse
import com.dicoding.storyapp.core.data.source.remote.request.LoginRequest
import com.dicoding.storyapp.core.data.source.remote.request.RegisterRequest
import com.dicoding.storyapp.core.data.source.remote.response.LoginResponse
import com.dicoding.storyapp.core.data.source.remote.response.MessageResponse
import com.dicoding.storyapp.core.domain.repository.IUserRepository
import kotlinx.coroutines.flow.Flow

class UserInteractor(private val userRepository: IUserRepository): UserUseCase {
    override fun register(registerRequest: RegisterRequest):
            Flow<ApiResponse<MessageResponse>> = userRepository.register(registerRequest)
    override fun login(loginRequest: LoginRequest): Flow<ApiResponse<LoginResponse>> =
        userRepository.login(loginRequest)
    override suspend fun setLogin(user: UserEntity) = userRepository.setLogin(user)
    override fun getLogin(): Flow<UserEntity> = userRepository.getLogin()
    override suspend fun deleteLogin() = userRepository.deleteLogin()
}