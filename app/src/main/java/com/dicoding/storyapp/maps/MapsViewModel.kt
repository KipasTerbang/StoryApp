package com.dicoding.storyapp.maps

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.dicoding.storyapp.core.data.source.local.entity.UserEntity
import com.dicoding.storyapp.core.domain.usecase.story.StoryUseCase
import com.dicoding.storyapp.core.domain.usecase.user.UserUseCase
import kotlinx.coroutines.launch

class MapsViewModel(
    private val userUseCase: UserUseCase,
    private val storyUseCase: StoryUseCase
    ): ViewModel() {

    fun getLogin(): LiveData<UserEntity> = userUseCase.getLogin().asLiveData()

    fun deleteLogin() { viewModelScope.launch { userUseCase.deleteLogin() } }

    fun getAllStoriesWithLocation(token: String) =
        storyUseCase.getAllStoriesWithLocation(token).asLiveData()
}