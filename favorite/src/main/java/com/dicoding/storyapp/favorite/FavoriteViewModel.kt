package com.dicoding.storyapp.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.dicoding.storyapp.core.domain.model.Story
import com.dicoding.storyapp.core.domain.usecase.story.StoryUseCase

class FavoriteViewModel(private val storyUseCase: StoryUseCase): ViewModel() {

    fun getBookmarkedStories() = storyUseCase.getBookmarkedStories().asLiveData()

    fun saveStory(story: Story) = storyUseCase.setStoryBookmark(story, true)

    fun deleteStory(story: Story) = storyUseCase.setStoryBookmark(story, false)
}