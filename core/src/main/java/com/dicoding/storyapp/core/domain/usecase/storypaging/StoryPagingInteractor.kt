package com.dicoding.storyapp.core.domain.usecase.storypaging

import androidx.paging.PagingData
import com.dicoding.storyapp.core.domain.model.Story
import com.dicoding.storyapp.core.domain.repository.IStoryPagingRepository
import kotlinx.coroutines.flow.Flow

class StoryPagingInteractor(
    private val storyPagingRepository: IStoryPagingRepository
    ): StoryPagingUseCase {
    override fun getAllStories(token: String): Flow<PagingData<Story>> =
        storyPagingRepository.getAllStories(token)
}