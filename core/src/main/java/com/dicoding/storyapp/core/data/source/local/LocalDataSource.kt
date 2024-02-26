package com.dicoding.storyapp.core.data.source.local

import androidx.lifecycle.LiveData
import androidx.paging.PagingSource
import com.dicoding.storyapp.core.data.source.local.datastore.UserPreferences
import com.dicoding.storyapp.core.data.source.local.entity.StoryEntity
import com.dicoding.storyapp.core.data.source.local.entity.UserEntity
import com.dicoding.storyapp.core.data.source.local.room.StoryDao
import kotlinx.coroutines.flow.Flow

class LocalDataSource(
    private val userPreferences: UserPreferences,
    private val storyDao: StoryDao
    ) {

    suspend fun setLogin(userEntity: UserEntity) = userPreferences.setLogin(userEntity)

    fun getLogin(): Flow<UserEntity> = userPreferences.getLogin()

    suspend fun deleteLogin() = userPreferences.deleteLogin()

    fun getAllStories(): PagingSource<Int, StoryEntity> = storyDao.getAllStories()

    fun getBookmarkedStories(): Flow<List<StoryEntity>> = storyDao.getBookmarkedStories()

    fun getAllStoriesWithLocation(): LiveData<List<StoryEntity>> =
        storyDao.getAllStoriesWithLocation()

    fun getDetailStory(id: String): LiveData<StoryEntity> = storyDao.getDetailStory(id)

    fun insertStory(stories: List<StoryEntity>) = storyDao.insertStory(stories)

    fun updateStory(story: StoryEntity) = storyDao.updateStory(story)

    fun deleteAll() = storyDao.deleteAll()

    fun isStoryBookmarked(id: String): Boolean = storyDao.isStoryBookmarked(id)
}