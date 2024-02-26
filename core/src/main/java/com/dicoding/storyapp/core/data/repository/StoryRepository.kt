package com.dicoding.storyapp.core.data.repository

import com.dicoding.storyapp.core.data.source.local.LocalDataSource
import com.dicoding.storyapp.core.data.source.remote.RemoteDataSource
import com.dicoding.storyapp.core.data.source.remote.network.ApiResponse
import com.dicoding.storyapp.core.data.source.remote.request.NewStoryRequest
import com.dicoding.storyapp.core.data.source.remote.response.AllStoriesResponse
import com.dicoding.storyapp.core.data.source.remote.response.DetailStoryResponse
import com.dicoding.storyapp.core.data.source.remote.response.MessageResponse
import com.dicoding.storyapp.core.domain.model.Story
import com.dicoding.storyapp.core.domain.repository.IStoryRepository
import com.dicoding.storyapp.core.utils.AppExecutors
import com.dicoding.storyapp.core.utils.StoryDataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class StoryRepository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
    ): IStoryRepository {

    override fun addNewStory(
        token: String?,
        newStoryRequest: NewStoryRequest
    ): Flow<ApiResponse<MessageResponse>> =
        remoteDataSource.addNewStory(token, newStoryRequest)

    override fun setStoryBookmark(story: Story, bookmarkState: Boolean) {
        val storyEntity = StoryDataMapper.mapDomainToEntity(story)
        storyEntity.isBookmarked = bookmarkState
        appExecutors.diskIO().execute { localDataSource.updateStory(storyEntity) }
    }

    override fun getBookmarkedStories() : Flow<List<Story>> =
        localDataSource.getBookmarkedStories().map {
            StoryDataMapper.mapEntitiesToDomain(it)
        }

    override fun getDetailStory(token: String, id: String) : Flow<ApiResponse<DetailStoryResponse>> =
        remoteDataSource.getDetailStory(token, id)

    override fun getAllStoriesWithLocation(token: String): Flow<ApiResponse<AllStoriesResponse>> =
        remoteDataSource.getAllStoriesWithLocation(token)
}