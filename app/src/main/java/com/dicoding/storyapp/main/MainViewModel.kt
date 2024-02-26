package com.dicoding.storyapp.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.dicoding.storyapp.core.data.source.local.datastore.SettingPreferences
import com.dicoding.storyapp.core.data.source.local.entity.UserEntity
import com.dicoding.storyapp.core.data.source.remote.request.LoginRequest
import com.dicoding.storyapp.core.data.source.remote.request.RegisterRequest
import com.dicoding.storyapp.core.domain.usecase.user.UserUseCase
import kotlinx.coroutines.launch

class MainViewModel(
    private val userUseCase: UserUseCase,
    private val settingPreferences: SettingPreferences
    ) : ViewModel() {
    fun register(registerRequest: RegisterRequest) = userUseCase.register(registerRequest).asLiveData()
    fun login(loginRequest: LoginRequest) = userUseCase.login(loginRequest).asLiveData()
    fun setLogin(userEntity: UserEntity) = viewModelScope.launch {
        userUseCase.setLogin(userEntity)
    }
    fun getLogin() : LiveData<UserEntity> = userUseCase.getLogin().asLiveData()
    fun deleteLogin() { viewModelScope.launch { userUseCase.deleteLogin() } }

    fun getThemeSetting() : LiveData<Boolean> = settingPreferences.getThemeSetting().asLiveData()
}