package com.dicoding.storyapp.core.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.dicoding.storyapp.core.data.paging.StoryRemoteMediator
import com.dicoding.storyapp.core.data.source.local.room.StoryDatabase
import com.dicoding.storyapp.core.data.source.remote.network.ApiService
import com.dicoding.storyapp.core.domain.model.Story
import com.dicoding.storyapp.core.domain.repository.IStoryPagingRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class StoryPagingRepository(
    private val apiService: ApiService,
    private val storyDatabase: StoryDatabase
): IStoryPagingRepository {

    @OptIn(ExperimentalPagingApi::class)
    override fun getAllStories(token: String) : Flow<PagingData<Story>> =
        Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            remoteMediator = StoryRemoteMediator(
                storyDatabase,
                apiService,
                token
            ),
            pagingSourceFactory = {
                storyDatabase.storyDao().getAllStories()
            }
        ).flow.map { pagingData ->
            pagingData.map { storyEntity ->
                Story(
                    id = storyEntity.id,
                    name = storyEntity.name,
                    description = storyEntity.description,
                    photoUrl = storyEntity.photoUrl,
                    createdAt = storyEntity.createdAt,
                    lat = storyEntity.lat,
                    lon = storyEntity.lon,
                    isBookmarked = storyEntity.isBookmarked
                )
            }
        }
}