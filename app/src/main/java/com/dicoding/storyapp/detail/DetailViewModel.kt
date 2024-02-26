package com.dicoding.storyapp.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.dicoding.storyapp.core.data.source.local.entity.UserEntity
import com.dicoding.storyapp.core.data.source.remote.network.ApiResponse
import com.dicoding.storyapp.core.data.source.remote.response.DetailStoryResponse
import com.dicoding.storyapp.core.domain.model.Story
import com.dicoding.storyapp.core.domain.usecase.story.StoryUseCase
import com.dicoding.storyapp.core.domain.usecase.user.UserUseCase
import kotlinx.coroutines.launch

class DetailViewModel(
    private val userUseCase: UserUseCase,
    private val storyUseCase: StoryUseCase
    ) : ViewModel() {

    fun getLogin(): LiveData<UserEntity> = userUseCase.getLogin().asLiveData()

    fun deleteLogin() { viewModelScope.launch { userUseCase.deleteLogin() } }

    fun getDetailStory(token: String, id: String): LiveData<ApiResponse<DetailStoryResponse>> =
        storyUseCase.getDetailStory(token, id).asLiveData()

    fun saveStory(story: Story) = storyUseCase.setStoryBookmark(story, true)

    fun deleteStory(story: Story) = storyUseCase.setStoryBookmark(story, false)
}