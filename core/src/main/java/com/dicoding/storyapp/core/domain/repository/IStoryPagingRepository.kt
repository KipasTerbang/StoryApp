package com.dicoding.storyapp.core.domain.repository

import androidx.paging.PagingData
import com.dicoding.storyapp.core.domain.model.Story
import kotlinx.coroutines.flow.Flow

interface IStoryPagingRepository {
    fun getAllStories(token: String): Flow<PagingData<Story>>
}