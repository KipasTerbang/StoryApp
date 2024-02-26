package com.dicoding.storyapp.core.data.source.local.room

import androidx.lifecycle.LiveData
import androidx.paging.PagingSource
import androidx.room.*
import com.dicoding.storyapp.core.data.source.local.entity.StoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface StoryDao {
    @Query("SELECT * FROM stories ORDER BY created_at DESC")
    fun getAllStories(): PagingSource<Int, StoryEntity>

    @Query("SELECT * FROM stories WHERE lat != NULL AND lon != NULL")
    fun getAllStoriesWithLocation(): LiveData<List<StoryEntity>>

    @Query("SELECT * FROM stories WHERE id = :id")
    fun getDetailStory(id: String): LiveData<StoryEntity>

    @Query("SELECT * FROM stories where is_bookmarked = 1")
    fun getBookmarkedStories(): Flow<List<StoryEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertStory(stories: List<StoryEntity>)

    @Update
    fun updateStory(story: StoryEntity)

    @Query("DELETE FROM stories WHERE is_bookmarked = 0")
    fun deleteAll()

    @Query("SELECT EXISTS(SELECT * FROM stories WHERE id = :id AND is_bookmarked = 1)")
    fun isStoryBookmarked(id: String): Boolean
}