package com.dicoding.storyapp.core.data.repository

import com.dicoding.storyapp.core.data.source.local.LocalDataSource
import com.dicoding.storyapp.core.data.source.local.entity.UserEntity
import com.dicoding.storyapp.core.data.source.remote.RemoteDataSource
import com.dicoding.storyapp.core.data.source.remote.network.ApiResponse
import com.dicoding.storyapp.core.data.source.remote.request.LoginRequest
import com.dicoding.storyapp.core.data.source.remote.request.RegisterRequest
import com.dicoding.storyapp.core.data.source.remote.response.LoginResponse
import com.dicoding.storyapp.core.data.source.remote.response.MessageResponse
import com.dicoding.storyapp.core.domain.repository.IUserRepository
import kotlinx.coroutines.flow.Flow

class UserRepository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
    ): IUserRepository {

    override fun register(registerRequest: RegisterRequest):
            Flow<ApiResponse<MessageResponse>> = remoteDataSource.register(registerRequest)

    override fun login(loginRequest: LoginRequest): Flow<ApiResponse<LoginResponse>> =
        remoteDataSource.login(loginRequest)

    override suspend fun setLogin(userEntity: UserEntity) = localDataSource.setLogin(userEntity)

    override fun getLogin(): Flow<UserEntity> = localDataSource.getLogin()

    override suspend fun deleteLogin() = localDataSource.deleteLogin()
}