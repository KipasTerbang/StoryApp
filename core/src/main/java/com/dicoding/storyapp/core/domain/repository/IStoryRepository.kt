package com.dicoding.storyapp.core.domain.repository

import com.dicoding.storyapp.core.data.source.remote.network.ApiResponse
import com.dicoding.storyapp.core.data.source.remote.request.NewStoryRequest
import com.dicoding.storyapp.core.data.source.remote.response.AllStoriesResponse
import com.dicoding.storyapp.core.data.source.remote.response.DetailStoryResponse
import com.dicoding.storyapp.core.data.source.remote.response.MessageResponse
import com.dicoding.storyapp.core.domain.model.Story
import kotlinx.coroutines.flow.Flow

interface IStoryRepository {
    fun addNewStory(token: String? = null, newStoryRequest: NewStoryRequest): Flow<ApiResponse<MessageResponse>>
    fun setStoryBookmark(story: Story, bookmarkState: Boolean)
    fun getBookmarkedStories(): Flow<List<Story>>
    fun getDetailStory(token: String, id: String): Flow<ApiResponse<DetailStoryResponse>>
    fun getAllStoriesWithLocation(token: String): Flow<ApiResponse<AllStoriesResponse>>
}