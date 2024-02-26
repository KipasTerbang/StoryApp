package com.dicoding.storyapp.core.domain.usecase.story

import com.dicoding.storyapp.core.data.source.remote.network.ApiResponse
import com.dicoding.storyapp.core.data.source.remote.request.NewStoryRequest
import com.dicoding.storyapp.core.data.source.remote.response.AllStoriesResponse
import com.dicoding.storyapp.core.data.source.remote.response.DetailStoryResponse
import com.dicoding.storyapp.core.data.source.remote.response.MessageResponse
import com.dicoding.storyapp.core.domain.model.Story
import com.dicoding.storyapp.core.domain.repository.IStoryRepository
import kotlinx.coroutines.flow.Flow

class StoryInteractor(private val storyRepository: IStoryRepository): StoryUseCase {
    override fun addNewStory(
        token: String?,
        newStoryRequest: NewStoryRequest
    ): Flow<ApiResponse<MessageResponse>> = storyRepository.addNewStory(token, newStoryRequest)

    override fun getBookmarkedStories(): Flow<List<Story>> =
        storyRepository.getBookmarkedStories()
    override fun setStoryBookmark(story: Story, bookmarkState: Boolean) =
        storyRepository.setStoryBookmark(story, bookmarkState)
    override fun getDetailStory(token: String, id: String): Flow<ApiResponse<DetailStoryResponse>> =
        storyRepository.getDetailStory(token, id)

    override fun getAllStoriesWithLocation(token: String): Flow<ApiResponse<AllStoriesResponse>> =
        storyRepository.getAllStoriesWithLocation(token)
}