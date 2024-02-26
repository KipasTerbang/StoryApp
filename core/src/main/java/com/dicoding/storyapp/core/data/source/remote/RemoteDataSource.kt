package com.dicoding.storyapp.core.data.source.remote

import android.util.Log
import com.dicoding.storyapp.core.data.source.remote.network.ApiResponse
import com.dicoding.storyapp.core.data.source.remote.network.ApiService
import com.dicoding.storyapp.core.data.source.remote.request.LoginRequest
import com.dicoding.storyapp.core.data.source.remote.request.NewStoryRequest
import com.dicoding.storyapp.core.data.source.remote.request.RegisterRequest
import com.dicoding.storyapp.core.data.source.remote.response.AllStoriesResponse
import com.dicoding.storyapp.core.data.source.remote.response.DetailStoryResponse
import com.dicoding.storyapp.core.data.source.remote.response.LoginResponse
import com.dicoding.storyapp.core.data.source.remote.response.MessageResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class RemoteDataSource(private val apiService: ApiService){

    fun register(registerRequest: RegisterRequest): Flow<ApiResponse<MessageResponse>> = flow {
        emit(ApiResponse.Empty)
        try {
            val response = apiService.register(registerRequest)
            emit(ApiResponse.Success(response))
        } catch (exception: Exception) {
            Log.e("RemoteDataSource", "register: ${exception.message.toString()}")
            emit(ApiResponse.Error(exception.message.toString()))
        }
    }.flowOn(Dispatchers.IO)

    fun login(loginRequest: LoginRequest): Flow<ApiResponse<LoginResponse>> = flow {
        emit(ApiResponse.Empty)
        try {
            val response = apiService.login(loginRequest)
            emit(ApiResponse.Success(response))
        } catch (exception: Exception) {
            Log.e("RemoteDataSource", "login: ${exception.message.toString()}")
        }
    }.flowOn(Dispatchers.IO)

    fun addNewStory(
        token: String? = null,
        newStoryRequest: NewStoryRequest
    ): Flow<ApiResponse<MessageResponse>> =
        flow {
            emit(ApiResponse.Empty)
            try {
                if (token != null) {
                    if (token.isNotBlank()) {
                        val response = apiService.addNewStory(
                            "Bearer $token",
                            newStoryRequest.description,
                            newStoryRequest.photo
                        )
                        emit(ApiResponse.Success(response))
                    } else {
                        val response = apiService.addNewStory(
                            newStoryRequest.description,
                            newStoryRequest.photo
                        )
                        emit(ApiResponse.Success(response))
                    }
                }
            } catch (e: Exception) {
                Log.d("RemoteDataSource", "addNewStory: ${e.message.toString()}")
                emit(ApiResponse.Error(e.message.toString()))
            }
        }.flowOn(Dispatchers.IO)

    fun getDetailStory(token: String, id: String): Flow<ApiResponse<DetailStoryResponse>> = flow {
        emit(ApiResponse.Empty)
        try {
            val response = apiService.getDetailStory("Bearer $token", id)
            emit(ApiResponse.Success(response))
        } catch (exception: Exception) {
            Log.d("RemoteDataSource", "getDetailStory: ${exception.message.toString()}")
            emit(ApiResponse.Error(exception.message.toString()))
        }
    }.flowOn(Dispatchers.IO)

    fun getAllStoriesWithLocation(token: String): Flow<ApiResponse<AllStoriesResponse>> = flow {
        emit(ApiResponse.Empty)
        try {
            val response = apiService.getAllStoriesWithLocation(
                "Bearer $token"
            )
            emit(ApiResponse.Success(response))
        } catch (exception: Exception) {
            Log.d("RemoteDataSource", "getAllStoriesWithLocation: ${exception.message.toString()}")
            emit(ApiResponse.Error(exception.message.toString()))
        }
    }.flowOn(Dispatchers.IO)
}