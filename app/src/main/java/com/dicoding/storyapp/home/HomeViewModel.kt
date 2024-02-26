package com.dicoding.storyapp.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.dicoding.storyapp.core.data.source.local.entity.UserEntity
import com.dicoding.storyapp.core.domain.model.Story
import com.dicoding.storyapp.core.domain.usecase.story.StoryUseCase
import com.dicoding.storyapp.core.domain.usecase.storypaging.StoryPagingUseCase
import com.dicoding.storyapp.core.domain.usecase.user.UserUseCase
import kotlinx.coroutines.launch

class HomeViewModel(
    private val userUseCase: UserUseCase,
    private val storyUseCase: StoryUseCase,
    private val storyPagingUseCase: StoryPagingUseCase
    ) : ViewModel() {

    fun getLogin() : LiveData<UserEntity> = userUseCase.getLogin().asLiveData()

    fun deleteLogin() { viewModelScope.launch { userUseCase.deleteLogin() } }

    fun getAllStories(token: String): LiveData<PagingData<Story>> =
        storyPagingUseCase.getAllStories(token).cachedIn(viewModelScope).asLiveData()

    fun saveStory(story: Story) = storyUseCase.setStoryBookmark(story, true)

    fun deleteStory(story: Story) = storyUseCase.setStoryBookmark(story, false)
}