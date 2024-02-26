package com.dicoding.storyapp.core.domain.usecase.storypaging

import androidx.paging.PagingData
import com.dicoding.storyapp.core.domain.model.Story
import kotlinx.coroutines.flow.Flow

interface StoryPagingUseCase {
    fun getAllStories(token: String): Flow<PagingData<Story>>
}